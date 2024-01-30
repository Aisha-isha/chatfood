package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.Message;
import com.example.demo.Model.Plan;
import com.example.demo.Model.Response;
import com.example.demo.Model.User;
import com.example.demo.Repository.PlanRepository;
import com.example.demo.Service.UserService;
import com.mysql.cj.Messages;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.ArrayList;


@Controller
public class ChatController {
    
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userservice;
    @Autowired
    private PlanRepository planrepository;
   
    
    @GetMapping("/")
    public String chatForm(Model model,HttpSession session) {
    	  if (session.getAttribute("userId") != null) {
    		  Long userId = (Long) session.getAttribute("userId");
 			 model.addAttribute("userId", userId );
    		 User existingUser = userservice.getUserById(userId);
    		 String age=existingUser.getAge();
 		 String goal=existingUser.getGoal();
 		 String problem=existingUser.getProblem();
 		 String status=existingUser.getStatus();
 		 String gender=existingUser.getGender();
 		 if(age==null) {
 			 return "redirect:/wizard"; 
 			 
 		 }
 		 else {
 			  List<Message> userMessages = (List<Message>) session.getAttribute("messages");
 			   model.addAttribute("messages", userMessages);
        model.addAttribute("message", new Message());
        model.addAttribute("age", age);
		  model.addAttribute("goal", goal);
		  model.addAttribute("problem", problem);
		  model.addAttribute("status", status);
		  model.addAttribute("gender", gender);
        return "chat";
    	  }
    	  }
    	  return "redirect:/signin";
    }

   

    @PostMapping("/chat")
    public String sendMessage(@ModelAttribute Message message, Model model, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        if (session.getAttribute("userId") != null) {

//
            Long userId = (Long) session.getAttribute("userId");
			 model.addAttribute("userId", userId );
   		 User existingUser = userservice.getUserById(userId);
   		 String age=existingUser.getAge();
		 String goal=existingUser.getGoal();
		 String problem=existingUser.getProblem();
		 String status=existingUser.getStatus();
		 String gender=existingUser.getGender();
		 if(age==null) {
			 return "redirect:/wizard"; 
			 
		 }
		 else {
			
       
            String url = "http://localhost:5005/webhooks/rest/webhook";
            HttpEntity<Message> requestEntity = new HttpEntity<>(message, headers);
            ResponseEntity<Response[]> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Response[].class);
            Response[] responses = responseEntity.getBody();

      
            List<Message> userMessages = (List<Message>) session.getAttribute("messages");
            if (userMessages == null) {
                userMessages = new ArrayList<>();
            }

     
            message.setSent(true);          
         
            userMessages.add(message);


            for (Response response : responses) {
                Message responseMessage = new Message();
             
               
                
                
                
                responseMessage.setMessage(response.getText());
 if ((response.getText().equals("Your allergies have been stored successfully. I will give you a food planner."))|| (response.getText().equals("Okay! I will plan"))) {
                    
                    List<Plan> plans = planrepository.findAll();
                    for (Plan plan : plans) {
                        if (plan.getId_user() == null) {
                            plan.setId_user(existingUser.getId());
                            planrepository.save(plan);
                        }
                    }
                }
                responseMessage.setSent(false); 
         
                userMessages.add(responseMessage);
            }

          
            session.setAttribute("messages", userMessages);

            model.addAttribute("message", new Message());
            model.addAttribute("messages", userMessages);
           
            model.addAttribute("age", age);
			  model.addAttribute("goal", goal);
			  model.addAttribute("problem", problem);
			  model.addAttribute("status", status);
			  model.addAttribute("gender", gender);
            return "chat";
        }
        }

        return "redirect:/signin";
    }
    
    
}