package net.springjpa.mvc.rac.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import net.springjpa.mvc.rac.model.User;
import net.springjpa.mvc.rac.service.UserService;

/**
 * REST Controller <br>
 * 
 * Links: <br>
 * GET    - <b>http://localhost:8080/RequestAccount/</b> - test endpoint <br>
 * POST   - <b>http://localhost:8080/RequestAccount/user/</b> + <i>{user json}</i> - Creates new user <br>
 * GET    - <b>http://localhost:8080/RequestAccount/user/{id}</b> - Request user from DB <br>
 * PUT    - <b>http://localhost:8080/RequestAccount/user/{id}</b> + <i>{user json}</i> - Update user from DB <br>
 * DELETE - <b>http://localhost:8080/RequestAccount/user/{id}</b> - Delete user from DB <br>
 * GET    - <b>http://localhost:8080/RequestAccount/users/</b> - Retreive the list of users from DB <br>
 * 
 * @author SorinCarcalicea
 */

@RestController
public class RequestAccountController {
	
	private Logger LOGGER = Logger.getLogger(RequestAccountController.class.getName());

	@Autowired
	private UserService userService;

	/**
	 * Do not use this <br>
	 * GET - <b>http://localhost:8080/RequestAccount/</b> - test endpoint <br>
	 * 
	 * @return - Welcome string
	 */
	@RequestMapping("/")
	public String requestAccount() {

		return "Wellcome to RAC REST endpoint";

	}
	
	/**
	 * Create new user <br>
	 * POST - <b>http://localhost:8080/RequestAccount/user/</b><i>{user json}</i> - Creates new user <br>
	 * 
	 * @param json - user json from UI <br>
	 * 
	 * @return user json and Http Status (CONFLICT if user is present in data base, CREATED if user was created successfully)
	 */
	@RequestMapping(value = "/user/", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public ResponseEntity<Void> createUser(@RequestBody User json, UriComponentsBuilder ucBuilder) {
		
		LOGGER.log(Level.INFO, "Create user -> START");
		
		User foundUser = userService.findById(json.getID());
		if (foundUser != null) {
			
			LOGGER.log(Level.INFO, "Create user(Conflict) -> END");
			
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	    }
		
		userService.create(json);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(json.getID()).toUri());

        LOGGER.log(Level.INFO, "Create user -> END");
        
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}
	
	/**
	 * Retrieve user from data base <br>
	 * GET - <b>http://localhost:8080/RequestAccount/user/{id}</b> - Request user from DB <br>
	 * 
	 * @param id - id for user to retrieve <br>
	 * 
	 * @return - HttpStatus (NOT_FOUND or OK)
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		
		LOGGER.log(Level.INFO, "Get user -> START");
		
		User foundUser = userService.findById(id);
		if (foundUser == null) {
			
			LOGGER.log(Level.INFO, "Get user(Not found) -> END");
			
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		LOGGER.log(Level.INFO, "Get user -> END");
	    
		return new ResponseEntity<User>(foundUser, HttpStatus.OK);
		
	}
	
	/**
	 * Update user from data base <br>
	 * PUT - <b>http://localhost:8080/RequestAccount/user/{id}</b> + <i>{user json}</i> - Update user from DB <br>
	 * 
	 * @param id - id for user to retrieve <br>
	 * 
	 * @return - HttpStatus (NOT_FOUND or OK) 
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		
		LOGGER.log(Level.INFO, "Update user -> START");
		
		User foundUser = userService.findById(id);
		if (foundUser == null) {
			
			LOGGER.log(Level.INFO, "Update user(Not found) -> END");
			
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		foundUser.setEmail(user.getEmail());
		foundUser.setFirstName(user.getFirstName());
		foundUser.setLastName(user.getLastName());
	    
		userService.update(foundUser);
		
		LOGGER.log(Level.INFO, "Update user -> END");
		
		return new ResponseEntity<User>(foundUser, HttpStatus.OK);
		
	}
	
	/**
	 * Delete user from database <br>
	 * DELETE - <b>http://localhost:8080/RequestAccount/user/{id}</b> - Delete user from DB <br>
	 * 
	 * @param id - id for user to delete <br>
	 * 
	 * @return - HttpStatus (NOT_FOUND or NO_CONTENT)
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
		
		LOGGER.log(Level.INFO, "Delete user -> START");
		 
		User foundUser = userService.findById(id);
        if (foundUser == null) {
        	
        	LOGGER.log(Level.INFO, "Delete user(Not found) -> END");
        	
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        userService.delete(id);
        
        LOGGER.log(Level.INFO, "Delete user -> END");
        
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
    /**
     * Get the list of all users <br>
     * 
     * GET - <b>http://localhost:8080/RequestAccount/users/</b> - Request all user from DB <br>
     * 
     * @return - json with list of all users and HttpStatus (NO_CONTENT if there are no users and OK for list with users)
     */
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
    	
    	LOGGER.log(Level.INFO, "List all users -> START");
    	
        List<User> users = userService.findAll();
        if(users.isEmpty()){
        	
        	LOGGER.log(Level.INFO, "List all users(No content) -> END");
        	
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        
        LOGGER.log(Level.INFO, "List all users -> END");
        
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
