package com.vishal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishal.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
		
}
