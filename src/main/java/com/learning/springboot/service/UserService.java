package com.learning.springboot.service;

import com.learning.springboot.model.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);

}
