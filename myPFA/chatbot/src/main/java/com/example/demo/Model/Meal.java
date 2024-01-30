package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Meal {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id_meal;
	 
	  @Column(name = "name_meal")
	    private String name_meal;
	    
	  @Column(name = "ingrediant_meal")
	    private String ingrediant_meal;
	  
	  @Column(name = "calories_meal")
	    private int calories_meal;
	  
	  @Column(name = "quantity_of_sugar_meal")
	    private int quantity_of_sugar_meal;
	  
	  @Column(name = "time_meal")
	    private String time_meal;

	public Long getId_meal() {
		return id_meal;
	}

	public void setId_meal(Long id_meal) {
		this.id_meal = id_meal;
	}

	public String getName_meal() {
		return name_meal;
	}

	public void setName_meal(String name_meal) {
		this.name_meal = name_meal;
	}

	public String getIngrediant_meal() {
		return ingrediant_meal;
	}

	public void setIngrediant_meal(String ingrediant_meal) {
		this.ingrediant_meal = ingrediant_meal;
	}

	public int getCalories_meal() {
		return calories_meal;
	}

	public void setCalories_meal(int calories_meal) {
		this.calories_meal = calories_meal;
	}

	public int getQuantity_of_sugar_meal() {
		return quantity_of_sugar_meal;
	}

	public void setQuantity_of_sugar_meal(int quantity_of_sugar_meal) {
		this.quantity_of_sugar_meal = quantity_of_sugar_meal;
	}

	public String getTime_meal() {
		return time_meal;
	}

	public void setTime_meal(String time_meal) {
		this.time_meal = time_meal;
	}
	  
	 
	
	

}
