package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.Service.MealPlanService;
import jakarta.servlet.http.HttpSession;

@Controller
public class loginController {
	 @Autowired
	    private UserService userService;
	 @Autowired
	    private MealPlanService  mealPlanService;
	 @Autowired
	    private UserRepository userRepository;

	 
	 
//    @GetMapping("/signin")
//   public String showLoginPage() {
//
//	    return "signin";
//    }
	  @GetMapping("/signin")
      public String showSignInPage(HttpSession session) {
		  if (session.getAttribute("userId") != null) {
			  return "redirect:/index";
		   
		}
		  return "signin";
	  }
    
    @GetMapping("/index")
    public String Show2(Model model,HttpSession session) {
    	 if (session.getAttribute("userId") != null) {
    		 Long userId = (Long) session.getAttribute("userId");
			 model.addAttribute("userId", userId );
    		 User existingUser = userService.getUserById(userId);
    		
    		 String age=existingUser.getAge();
    		 String goal=existingUser.getGoal();
    		 String problem=existingUser.getProblem();
    		 String status=existingUser.getStatus();
    		 String gender=existingUser.getGender();
    		 
    		 if(age==null) {
    			 return "redirect:/wizard"; 
    			 
    		 }
    		 else {
    			  model.addAttribute("gender", gender);
    			  model.addAttribute("age", age);
    			  model.addAttribute("goal", goal);
    			  model.addAttribute("problem", problem);
    			  model.addAttribute("status", status);
    			
    				 return "index"; 
    		 }
		}
    	 return "redirect:/signin";
    }
    
    
    
    
   
	@PostMapping("/login_error")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();

        
        
       
        User existingUser = userRepository.getCurrentUser(username);

    if (existingUser == null || !existingUser.getPassword().equals(password)) {
        model.addAttribute("error", "Invalid username or password");
        return "signin";
    }

        

        session.setAttribute("userId", existingUser.getId());
        model.addAttribute("currentUser", existingUser);
        if (existingUser.getAge() == null) {
            return "redirect:/wizard";
        } else {
     
            return "redirect:/index";
        }
    }



//	 private boolean isPasswordValid(String password, String hashedPassword) {
//		    String hashedProvidedPassword = userService.hashPassword(password);
//		    return hashedProvidedPassword.equals(hashedPassword);
//		}

  

    
}