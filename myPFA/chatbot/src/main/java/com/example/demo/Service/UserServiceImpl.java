package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
       
    	
    	
//        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        User existingUser = userRepository.findByUsername(username);
        return existingUser == null;
    }

//    public String hashPassword(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hashedBytes = md.digest(password.getBytes());
//
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hashedBytes) {
//                sb.append(String.format("%02x", b));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
//        }
//    }
    
    
   

    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(Long userId) {
      
    	 List<User> users = getAllUsers();
    	 for (User user : users) {
    	 	 if (user.getId() == userId){
    	         return user;
    }
    	 }
    	 return null;
    }
}
