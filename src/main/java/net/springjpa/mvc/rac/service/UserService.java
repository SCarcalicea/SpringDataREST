package net.springjpa.mvc.rac.service;

import java.util.List;

import net.springjpa.mvc.rac.model.User;

public interface UserService {
	
	public User create(User user);
	public User delete(int id);
	public List<User> findAll();
	public User update(User user);
	public User findById(Integer id);

}
