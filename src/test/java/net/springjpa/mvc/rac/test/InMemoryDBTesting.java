package net.springjpa.mvc.rac.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import net.springjpa.mvc.rac.model.User;
import net.springjpa.mvc.rac.repository.UserRepository;
import net.springjpa.mvc.rac.test.configuration.TestingConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  classes = { TestingConfiguration.class }, 
  loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBTesting {
     
    @Resource
    private UserRepository userRepository;
     
    @Test
    public void givenUser_CRUD_operations() {
    	// Create
        User user = new User(1, "unit", "testing", "pass");
        userRepository.save(user);
         
        // Read
        User foundInBD = userRepository.getOne(1);
        assertNotNull("Create/read operation failed", foundInBD);
        
        // Update
        foundInBD.setEmail("updatedEmail");
        foundInBD = userRepository.getOne(1);
        assertTrue("Update operation failed", foundInBD.getEmail().equals("updatedEmail"));
        
        // Delete
        userRepository.delete(foundInBD);
        List<User> finalResult = userRepository.findAll();
        assertTrue("Delete operation faile", finalResult.isEmpty());
    }
}

