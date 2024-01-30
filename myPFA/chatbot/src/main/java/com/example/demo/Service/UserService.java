package com.example.demo.Service;



import java.util.List;

import com.example.demo.Model.User;

public interface UserService {

	void saveUser(User user);
    boolean isUsernameUnique(String username);
//    String hashPassword(String password);
    public List<User> getAllUsers();
	public User getUserById(Long userId);
}
