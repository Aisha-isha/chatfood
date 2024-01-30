package com.example.demo.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.demo.Model.User;
import com.example.demo.Service.UserService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
@Controller
public class WizardController {

	@Autowired
	UserService userservice;
	
	 @GetMapping("/wizard")
	    public String Show(HttpSession session,Model model) {
		 if (session.getAttribute("userId") != null) {
			 Long userId = (Long) session.getAttribute("userId");
			 model.addAttribute("userId", userId );
			    return "wizard";
			  
		   
		}
    	 return "redirect:/signin";
		 
	    }
	 @PostMapping("/save")
	 public String saveData(@ModelAttribute("user") User user, Model model, HttpSession session) {
	     Long userId = (Long) session.getAttribute("userId");
	     User existingUser = userservice.getUserById(userId);
	     
	     if (existingUser != null) {
	       
	             existingUser.setAge(user.getAge());
	             existingUser.setStatus(user.getStatus());
	             existingUser.setGoal(user.getGoal());
	             existingUser.setProblem(user.getProblem());
	             existingUser.setGender(user.getGender());
	             userservice.saveUser(existingUser);
	             return "redirect:/index";
	       
	     } else {
	         return "index";
	     }
	 }


}

