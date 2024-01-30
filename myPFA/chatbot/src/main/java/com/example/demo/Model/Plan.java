package com.example.demo.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Plan {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 @Column(name = "id_user")
	    private Long id_user;
	 
	 @Column(name = "Allergy")
	    private String Allergy;
	 
	 @Column(name = "is_yes")
	    private String is_yes;
	 
	 @Column(name = "breakfast")
	    private String breakfast;
	 
	 @Column(name = "lunch")
	    private String lunch;
	 
	 @Column(name = "dinner")
	    private String dinner;

	 public Plan() {
		 
	 }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public String getAllergy() {
		return Allergy;
	}

	public void setAllergy(String ingrediants) {
		this.Allergy = ingrediants;
	}

	public String getIs_yes() {
		return is_yes;
	}

	public void setIs_yes(String is_yes) {
		this.is_yes = is_yes;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}
	 
}
