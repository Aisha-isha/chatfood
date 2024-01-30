package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;
@ControllerAdvice
public class GlobalControllerAdvice {

	    @Autowired
	    private UserRepository userRepository;

	    @ModelAttribute("currentUser")
	    public User getCurrentUser(HttpSession session) {
	        Long userId = (Long) session.getAttribute("userId");
	        if (userId != null) {
	            return userRepository.findById(userId).orElse(null);
	        }
	        return null;
	    }
	}
	

