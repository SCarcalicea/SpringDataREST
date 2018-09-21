package net.springjpa.mvc.rac.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.springjpa.mvc.rac.model.User;
import net.springjpa.mvc.rac.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	UserRepository userRepo;

	@Override
	@Transactional
	public User create(User user) {
		User createdUser = user;
		return userRepo.save(createdUser);
	}

	@Override
	@Transactional
	public User delete(int id) {
		
		User deletedUser = userRepo.getOne(id);
        
        if (deletedUser == null)
            throw new IllegalArgumentException("(Delete)User not found!!!");
         
        userRepo.delete(deletedUser);
        
        return deletedUser;
	}

	@Override
	@Transactional
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	@Transactional
	public User update(User user) {
		
		User updateUser = userRepo.getOne(user.getID());
		
		if (updateUser == null)
			throw new IllegalArgumentException("(Update)User not found!!!");
		
		updateUser.setID(user.getID());
		updateUser.setFirstName(user.getFirstName());
		updateUser.setLastName(user.getLastName());
		updateUser.setEmail(user.getEmail());

		return updateUser;
	}

	@Override
	@Transactional
	public User findById(Integer id) {
		
		if (id == null) {
			return null;
		}
		
		Optional<User> user = userRepo.findById(id);
		return user.isPresent() ? user.get() : null;
	}
}
