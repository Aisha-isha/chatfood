package com.example.demo.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Meal;
import com.example.demo.Model.User;
import com.example.demo.Model.Plan;
import com.example.demo.Repository.MealRepository;
import com.example.demo.Repository.PlanRepository;
import com.example.demo.Model.ConstraintFolder.Constraint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class MealPlanService {

	  @Autowired
	    private MealRepository mealrepository ;
	  @Autowired
	    private PlanRepository planrepository;

    @Autowired
    private UserService userService;
    Plan trueplan = null;
    Boolean  is_diabete=false;

    public void generateMealPlan(Long userId,Plan trueplan) {
    	//variable qui sont les elements a generer
    	 List<String> variables = new ArrayList<>();
         variables.add("breakfast");
         variables.add("lunch");
         variables.add("dinner");
         
         //valeur des variable
        
        List<Meal> breakfastMeals = findByTimeMeal("breakfast");
        List<Meal> lunchMeals = findByTimeMeal("lunch");
        List<Meal> dinnerMeals = findByTimeMeal("dinner");
        //alegy
        List<Meal> domainbreakfast = new ArrayList<>();
        List<Meal> domainlunch = new ArrayList<>();
        List<Meal> domaindinner = new ArrayList<>();
        if(trueplan.getAllergy()!=null) {
        	//breakfast
        String allergyss =trueplan.getAllergy();
        String[] allergys=allergyss.split(",");
        for (Meal breakfast : breakfastMeals) {
        String[] ingrediants = breakfast.getIngrediant_meal().split(",");
        boolean foundMatch = false;
        for (String allergy : allergys) {
         for (String ingrediant : ingrediants) {
             if (ingrediant.contains(allergy)) {
                 foundMatch = true;
                 break;
             }
         }
         if (foundMatch) {
              break;
         }
      }
        if (!foundMatch) {
        	domainbreakfast .add(breakfast);
      }
        }
        //lunch
     
        for (Meal lunch : lunchMeals) {
        String[] ingrediants2 = lunch.getIngrediant_meal().split(",");
        boolean foundMatch2 = false;
        for (String allergy : allergys) {
         for (String ingrediant2 : ingrediants2) {
             if (ingrediant2.contains(allergy)) {
                 foundMatch2 = true;
                 break;
             }
         }
         if (foundMatch2) {
              break;
         }
      }
        if (!foundMatch2) {
        	domainlunch .add(lunch);
      }
        }
        //dinner
        for (Meal dinner : dinnerMeals) {
            String[] ingrediants3 = dinner.getIngrediant_meal().split(",");
            boolean foundMatch3 = false;
            for (String allergy : allergys) {
             for (String ingrediant3 : ingrediants3) {
                 if (ingrediant3.contains(allergy)) {
                     foundMatch3 = true;
                     break;
                 }
             }
             if (foundMatch3) {
                  break;
             }
          }
            if (!foundMatch3) {
            	domaindinner .add(dinner);
          }
            }
        
    }
        
    
        
        
        

      //domains formé par cle valeur : valeur que peut prendre chaque variable 
            Map<String, List<Meal>> domains = new HashMap<>();
            if(trueplan.getAllergy()==null) {
            domains.put("breakfast",breakfastMeals);
            domains.put("lunch",lunchMeals );
            domains.put("dinner", dinnerMeals);
            }
            else {
            	  domains.put("breakfast",domainbreakfast);
                  domains.put("lunch",domainlunch );
                  domains.put("dinner", domaindinner);
            }
          

            	User myuser = userService.getUserById(userId);
                String[] status	= myuser.getProblem().split(",");
                for(int i=0;i<status.length;i++) {
                	if(status[i]=="diabetic") {
                		is_diabete=true;
                	}
                		
                	
                }
            
         // Définition des contraintes
            List<Constraint> constraints = new ArrayList<>();
            User user = userService.getUserById(userId);
            if(user.getGender()=="female ") {
            	if(is_diabete) {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getQuantity_of_sugar_meal()+b.getQuantity_of_sugar_meal()+c.getQuantity_of_sugar_meal() < 15));

            	}
            	else {
            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getQuantity_of_sugar_meal()+b.getQuantity_of_sugar_meal()+c.getQuantity_of_sugar_meal() < 25));
            	}//ad
            if(user.getAge()=="adolescent" && user.getStatus()=="gain weight") {
            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2200 ));
            }
            if(user.getAge()=="adolescent" && user.getStatus()=="maintain weight") {
                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 1800 ));
                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2200 ));
                }
            if(user.getAge()=="adolescent" && user.getStatus()=="lose weight") {
                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 1800 ));
                }
            //jeune 
            if(user.getAge()=="Young adult" && user.getStatus()=="gain weight") {
                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2000 ));
                }
                if(user.getAge()=="Young adult" && user.getStatus()=="maintain weight") {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 1800 ));
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2200 ));
                    }
                if(user.getAge()=="Young adult" && user.getStatus()=="lose weight") {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 1800 ));
                    }
                //Middle-aged adult
              
                if(user.getAge()=="Middle-aged adult" && user.getStatus()=="gain weight") {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 1800 ));
                    }
                    if(user.getAge()=="Middle-aged adult" && user.getStatus()=="maintain weight") {
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 1600 ));
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2000 ));
                        }
                    if(user.getAge()=="Middle-aged adult" && user.getStatus()=="lose weight") {
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 1600 ));
                        }
                    //Elderly
                    if(user.getAge()=="Elderly" && user.getStatus()=="gain weight") {
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 1600 ));
                        }
                        if(user.getAge()=="Elderly" && user.getStatus()=="maintain weight") {
                            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 1400 ));
                            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2800 ));
                            }
                        if(user.getAge()=="Elderly" && user.getStatus()=="lose weight") {
                            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 1600 ));
                            }
            }
            
            else {
            	if(is_diabete) {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getQuantity_of_sugar_meal()+b.getQuantity_of_sugar_meal()+c.getQuantity_of_sugar_meal() < 28));

            	}
            	else {
                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getQuantity_of_sugar_meal()+b.getQuantity_of_sugar_meal()+c.getQuantity_of_sugar_meal() < 36));
            	}//ad
                if(user.getAge()=="adolescent" && user.getStatus()=="gain weight") {
                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2800 ));
                }
                if(user.getAge()=="adolescent" && user.getStatus()=="maintain weight") {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2200 ));
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2800 ));
                    }
                if(user.getAge()=="adolescent" && user.getStatus()=="lose weight") {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2200 ));
                    }
                //jeune 
                if(user.getAge()=="Young adult" && user.getStatus()=="gain weight") {
                    constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2800 ));
                    }
                    if(user.getAge()=="Young adult" && user.getStatus()=="maintain weight") {
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2400 ));
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2800 ));
                        }
                    if(user.getAge()=="Young adult" && user.getStatus()=="lose weight") {
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2200 ));
                        }
                    //Middle-aged adult
                  
                    if(user.getAge()=="Middle-aged adult" && user.getStatus()=="gain weight") {
                        constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2400 ));
                        }
                        if(user.getAge()=="Middle-aged adult" && user.getStatus()=="maintain weight") {
                            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2200 ));
                            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2400 ));
                            }
                        if(user.getAge()=="Middle-aged adult" && user.getStatus()=="lose weight") {
                            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2000 ));
                            }
                        //Elderly
                        if(user.getAge()=="Elderly" && user.getStatus()=="gain weight") {
                            constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 2000 ));
                            }
                            if(user.getAge()=="Elderly" && user.getStatus()=="maintain weight") {
                                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() > 1800 ));
                                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 2200 ));
                                }
                            if(user.getAge()=="Elderly" && user.getStatus()=="lose weight") {
                                constraints.add(new Constraint("breakfast", "lunch","dinner", (a,b,c) -> a.getCalories_meal()+b.getCalories_meal()+c.getCalories_meal() < 1800 ));
                                }

            }
        

            
            
            //CSP qui retourne une map string meal 
            List<Map<String, Meal>> solutions = new ArrayList<>();
            solveCSP(new HashMap<>(), variables, domains, constraints, solutions);

//        
            //stockage dans la table
            if (!solutions.isEmpty()) {
            	  Random random = new Random();
            	  List<Map<String, Meal>> randomSolutions = new ArrayList<>();

            	  while (randomSolutions.size() < 7) {
            	      Map<String, Meal> randomSolution = solutions.get(random.nextInt(solutions.size()));
            	      if (!randomSolutions.contains(randomSolution)) {
            	          randomSolutions.add(randomSolution);
            	      }
            	  }

            	  if (!randomSolutions.isEmpty()) {
            		  for (String variable : variables) {
            			  String Breakfast="";
            			  String lunch="";
            			  String dinner="";
            			  if(variable=="breakfast") {
            				  for(int i=0;i<=6;i++) {
            		    Map<String, Meal> randomSolution = randomSolutions.get(i);
                   	   Long domainValues = randomSolution.get(variable).getId_meal();
                   	   Breakfast=Breakfast+","+domainValues.toString();
            				  }
                  trueplan.setBreakfast(Breakfast);
                	}
                	if(variable=="lunch") {
                		 for(int i=0;i<=6;i++) {
                 		    Map<String, Meal> randomSolution = randomSolutions.get(i);
                        	   Long domainValues = randomSolution.get(variable).getId_meal();
                        	   lunch=lunch+","+domainValues.toString();
                 				  }
                       trueplan.setLunch(lunch);
                	}
                	if(variable=="dinner") {
                		for(int i=0;i<=6;i++) {
                 		    Map<String, Meal> randomSolution = randomSolutions.get(i);
                        	   Long domainValues = randomSolution.get(variable).getId_meal();
                        	   dinner= dinner+","+domainValues.toString();
                 				  }
                       trueplan.setDinner( dinner);
                	}
                  planrepository.save(trueplan);
                  
                }
            		}
                
                
//            }  
                
                
            } else {
               return;
            }
           
    }
    
   
    
  

    
    
 
    public static void solveCSP(Map<String, Meal> assignment, List<String> variables,
            Map<String, List<Meal>> domains, List<Constraint> constraints,
            List<Map<String, Meal>> solutions) {
    	
if (isComplete(assignment, variables)) {
solutions.add(new HashMap<>(assignment)); // Ajouter la solution trouvée
return;
}
    
String unassignedVar = selectUnassignedVariable(assignment, variables);
for (Meal value : orderDomainValues(unassignedVar, assignment, domains)) {
    assignment.put(unassignedVar, value);
    if (isConsistent(assignment, constraints)) {
        solveCSP(assignment, variables, domains, constraints, solutions);
    }
    assignment.remove(unassignedVar);
}
}
   
    
    private static List<Meal> orderDomainValues(String variable, Map<String, Meal> assignment,
            Map<String, List<Meal>> domains) {
return domains.get(variable);
}   
    
    
    
 
    
    private static boolean isComplete(Map<String, Meal> assignment, List<String> variables) {
        return assignment.size() == variables.size();
    }
 
    

    
    
    
    private static String selectUnassignedVariable(Map<String, Meal> assignment, List<String> variables) {
        for (String variable : variables) {
            if (!assignment.containsKey(variable)) {
                return variable;
            }
        }
        return null;
    }
   

 
    private static boolean isConsistent(Map<String, Meal> assignment, List<Constraint> constraints) {
        for (Constraint constraint : constraints) {
        	 Meal value1 = assignment.get(constraint.variable1);
           Meal value2 = assignment.get(constraint.variable2);
           Meal value3 = assignment.get(constraint.variable3);
            if (value1 != null && value2 != null && value3 != null && !constraint.test(value1, value2,value3)) {
                return false;
            }
        }
        return true;
    }
 
 
 //pour la recherche (hors CSP)
 
 private List<Meal> findByTimeMeal(String timeMeal) {
     List<Meal> meals = getAllMeals();
     List<Meal> filteredMeals = new ArrayList<>();

     for (Meal meal : meals) {
         if (meal.getTime_meal().equals(timeMeal)) {
             filteredMeals.add(meal);
         }
     }

     return filteredMeals;
 }

 public Meal findByMeal(String id) {
     List<Meal> meals = getAllMeals();
   

     for (Meal meal : meals) {
         if (meal.getId_meal().toString().equals(id)) {
             return meal;
         }
     }

     return null;
 }
 
 public List<Meal> getAllMeals() {
     return mealrepository.findAll();
 }
 
 //random

 private Meal getRandomMeal(List<Meal> meals) {
     Random random = new Random();
     int index = random.nextInt(meals.size());
     return meals.get(index);
 }








public List<Plan> findAllByUserId(Long userId) {
	List<Plan> plans = planrepository.findAll();
    List<Plan> filteredplans = new ArrayList<>();

    for (Plan plan : plans) {
        if (plan.getId_user()!=null && plan.getId_user().equals(userId)) {
        	filteredplans.add( plan);
        }
    }

    return filteredplans;
}

}