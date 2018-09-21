package net.springjpa.mvc.rac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.springjpa.mvc.rac.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}