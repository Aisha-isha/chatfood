package com.example.demo.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Model.Meal;
import com.example.demo.Model.Plan;
import com.example.demo.Model.User;
import com.example.demo.Repository.PlanRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.MealPlanService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlanController {

	@Autowired
    private UserService userService;
	
 @Autowired
    private MealPlanService  mealPlanService;
 @Autowired
    private PlanRepository planRepository;

	
	
	
	
	@GetMapping("/plan")
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
    			
    			
    			   //affichage du plan
    			   List<Plan> plans = mealPlanService.findAllByUserId(userId);
    			   Plan trueplan = null;
    			   if (!plans.isEmpty()) {
    			        // Triez les plans en ordre décroissant de l'ID
    			        Collections.sort(plans, (p1, p2) -> Long.compare(p2.getId(), p1.getId()));

    			      trueplan = plans.get(0);
if(trueplan.getBreakfast()==null) {
	
	  mealPlanService.generateMealPlan(existingUser.getId(),trueplan);
	  
    			        String breakfast = trueplan.getBreakfast();
    			        String lunch = trueplan.getLunch();
    			        String dinner = trueplan.getDinner();
    			        String[] breakfastItems = breakfast.split(",");
    			        String[] lunchItems = lunch.split(",");
    			        String[] dinnerItems = dinner.split(",");
    			        //moday
                   String breakfast1=breakfastItems[1];
                   Meal meal10 = mealPlanService.findByMeal(breakfast1);
                   
                   String lunch1=lunchItems[1];
                   Meal meal11 = mealPlanService.findByMeal(lunch1);
                   
                   String dinner1=dinnerItems[1];
                   Meal meal12 = mealPlanService.findByMeal(dinner1);
                   
                   model.addAttribute("breakfast1", meal10.getName_meal());
                   model.addAttribute("lunch1", meal11.getName_meal());
                   model.addAttribute("dinner1", meal12.getName_meal());
                   //tusday
                   String breakfast2=breakfastItems[2];
                   Meal meal20 = mealPlanService.findByMeal(breakfast2);
                   
                   String lunch2=lunchItems[2];
                   Meal meal21 = mealPlanService.findByMeal(lunch2);
                   
                   String dinner2=dinnerItems[2];
                   Meal meal22 = mealPlanService.findByMeal(dinner2);
                   
                   model.addAttribute("breakfast2", meal20.getName_meal());
                   model.addAttribute("lunch2", meal21.getName_meal());
                   model.addAttribute("dinner2", meal22.getName_meal());
                   //wensday
                   String breakfast3=breakfastItems[3];
                   Meal meal30 = mealPlanService.findByMeal(breakfast3);
                   
                   String lunch3=lunchItems[3];
                   Meal meal31 = mealPlanService.findByMeal(lunch3);
                   
                   String dinner3=dinnerItems[3];
                   Meal meal32 = mealPlanService.findByMeal(dinner3);
                   
                   model.addAttribute("breakfast3", meal30.getName_meal());
                   model.addAttribute("lunch3", meal31.getName_meal());
                   model.addAttribute("dinner3", meal32.getName_meal());
                   //jeudi 
                   String breakfast4=breakfastItems[4];
                   Meal meal40 = mealPlanService.findByMeal(breakfast4);
                   
                   String lunch4=lunchItems[4];
                   Meal meal41 = mealPlanService.findByMeal(lunch4);
                   
                   String dinner4=dinnerItems[4];
                   Meal meal42 = mealPlanService.findByMeal(dinner4);
                   
                   model.addAttribute("breakfast4", meal40.getName_meal());
                   model.addAttribute("lunch4", meal41.getName_meal());
                   model.addAttribute("dinner4", meal42.getName_meal());
                   //vendredi 
                   String breakfast5=breakfastItems[5];
                   Meal meal50 = mealPlanService.findByMeal(breakfast5);
                   
                   String lunch5=lunchItems[5];
                   Meal meal51 = mealPlanService.findByMeal(lunch5);
                   
                   String dinner5=dinnerItems[5];
                   Meal meal52 = mealPlanService.findByMeal(dinner5);
                   
                   model.addAttribute("breakfast5", meal50.getName_meal());
                   model.addAttribute("lunch5", meal51.getName_meal());
                   model.addAttribute("dinner5", meal52.getName_meal());
                   //samedi
                   String breakfast6=breakfastItems[6];
                   Meal meal60 = mealPlanService.findByMeal(breakfast6);
                   
                   String lunch6=lunchItems[6];
                   Meal meal61 = mealPlanService.findByMeal(lunch6);
                   
                   String dinner6=dinnerItems[6];
                   Meal meal62 = mealPlanService.findByMeal(dinner6);
                   
                   model.addAttribute("breakfast6", meal60.getName_meal());
                   model.addAttribute("lunch6", meal61.getName_meal());
                   model.addAttribute("dinner6", meal62.getName_meal());
                   //dimanche
                   String breakfast7=breakfastItems[7];
                   Meal meal70 = mealPlanService.findByMeal(breakfast7);
                   
                   String lunch7=lunchItems[7];
                   Meal meal71 = mealPlanService.findByMeal(lunch7);
                   
                   String dinner7=dinnerItems[7];
                   Meal meal72 = mealPlanService.findByMeal(dinner7);
                   
                   model.addAttribute("breakfast7", meal70.getName_meal());
                   model.addAttribute("lunch7", meal71.getName_meal());
                   model.addAttribute("dinner7", meal72.getName_meal());
                   return "plan"; 

	 
}
else {
	  String breakfast = trueplan.getBreakfast();
      String lunch = trueplan.getLunch();
      String dinner = trueplan.getDinner();
      String[] breakfastItems = breakfast.split(",");
      String[] lunchItems = lunch.split(",");
      String[] dinnerItems = dinner.split(",");
      //moday
 String breakfast1=breakfastItems[1];
 Meal meal10 = mealPlanService.findByMeal(breakfast1);
 
 String lunch1=lunchItems[1];
 Meal meal11 = mealPlanService.findByMeal(lunch1);
 
 String dinner1=dinnerItems[1];
 Meal meal12 = mealPlanService.findByMeal(dinner1);
 
 model.addAttribute("breakfast1", meal10.getName_meal());
 model.addAttribute("lunch1", meal11.getName_meal());
 model.addAttribute("dinner1", meal12.getName_meal());
 //tusday
 String breakfast2=breakfastItems[2];
 Meal meal20 = mealPlanService.findByMeal(breakfast2);
 
 String lunch2=lunchItems[2];
 Meal meal21 = mealPlanService.findByMeal(lunch2);
 
 String dinner2=dinnerItems[2];
 Meal meal22 = mealPlanService.findByMeal(dinner2);
 
 model.addAttribute("breakfast2", meal20.getName_meal());
 model.addAttribute("lunch2", meal21.getName_meal());
 model.addAttribute("dinner2", meal22.getName_meal());
 //wensday
 String breakfast3=breakfastItems[3];
 Meal meal30 = mealPlanService.findByMeal(breakfast3);
 
 String lunch3=lunchItems[3];
 Meal meal31 = mealPlanService.findByMeal(lunch3);
 
 String dinner3=dinnerItems[3];
 Meal meal32 = mealPlanService.findByMeal(dinner3);
 
 model.addAttribute("breakfast3", meal30.getName_meal());
 model.addAttribute("lunch3", meal31.getName_meal());
 model.addAttribute("dinner3", meal32.getName_meal());
 //jeudi 
 String breakfast4=breakfastItems[4];
 Meal meal40 = mealPlanService.findByMeal(breakfast4);
 
 String lunch4=lunchItems[4];
 Meal meal41 = mealPlanService.findByMeal(lunch4);
 
 String dinner4=dinnerItems[4];
 Meal meal42 = mealPlanService.findByMeal(dinner4);
 
 model.addAttribute("breakfast4", meal40.getName_meal());
 model.addAttribute("lunch4", meal41.getName_meal());
 model.addAttribute("dinner4", meal42.getName_meal());
 //vendredi 
 String breakfast5=breakfastItems[5];
 Meal meal50 = mealPlanService.findByMeal(breakfast5);
 
 String lunch5=lunchItems[5];
 Meal meal51 = mealPlanService.findByMeal(lunch5);
 
 String dinner5=dinnerItems[5];
 Meal meal52 = mealPlanService.findByMeal(dinner5);
 
 model.addAttribute("breakfast5", meal50.getName_meal());
 model.addAttribute("lunch5", meal51.getName_meal());
 model.addAttribute("dinner5", meal52.getName_meal());
 //samedi
 String breakfast6=breakfastItems[6];
 Meal meal60 = mealPlanService.findByMeal(breakfast6);
 
 String lunch6=lunchItems[6];
 Meal meal61 = mealPlanService.findByMeal(lunch6);
 
 String dinner6=dinnerItems[6];
 Meal meal62 = mealPlanService.findByMeal(dinner6);
 
 model.addAttribute("breakfast6", meal60.getName_meal());
 model.addAttribute("lunch6", meal61.getName_meal());
 model.addAttribute("dinner6", meal62.getName_meal());
 //dimanche
 String breakfast7=breakfastItems[7];
 Meal meal70 = mealPlanService.findByMeal(breakfast7);
 
 String lunch7=lunchItems[7];
 Meal meal71 = mealPlanService.findByMeal(lunch7);
 
 String dinner7=dinnerItems[7];
 Meal meal72 = mealPlanService.findByMeal(dinner7);
 
 model.addAttribute("breakfast7", meal70.getName_meal());
 model.addAttribute("lunch7", meal71.getName_meal());
 model.addAttribute("dinner7", meal72.getName_meal());
 return "plan"; 
 
	
}
                   
    			   }
    			   else {
    				   return "plan"; 
    			   }
    			  
    			   }  
    			   
    			   
    			   
    			   
    			   
    				
    		 }
		
    	 return "redirect:/signin";
    }
    
}
