package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Model.User;

import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {

	 @Autowired
	    private UserService userService;
	

     @GetMapping("/signup")
     public String showSignUpPage(@ModelAttribute("user") User user,Model model,HttpSession session) {
         if (session.getAttribute("userId") != null) {
       	 
             return "redirect:/index"; 
         }
         
         model.addAttribute("user", new User());
         return "signup";
	  
	    }


	 @PostMapping("/signup_error")
	    public String registerUser(@ModelAttribute("user") User user, Model model) {
	        if (!userService.isUsernameUnique(user.getUsername())) {
	            model.addAttribute("error", "This username is already in use");
	            return "signup";
	        }
	        userService.saveUser(user);
	        return "redirect:/signin";
	    }
	 
	
	
}
