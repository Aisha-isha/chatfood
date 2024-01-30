package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class acceuilController {
	
	
	
	@GetMapping("/home")
    public String Show() {
    	 return "acceuil";
	}

}
