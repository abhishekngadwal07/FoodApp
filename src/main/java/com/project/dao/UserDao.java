package com.project.dao;

import com.project.model.User;

public interface UserDao {
	int getUser(User user);
	User getUser(String name);
}
