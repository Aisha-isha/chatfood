package com.example.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
	public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name = "username")
	    private String username;
	    
	    @Column(name = "password")
	    private String password;

	    
	    @Column(name = "age")
	    private String age;
	    
	    @Column(name = "status")
	    private String status;
	    
	  

		@Column(name = "goal")
	    private String goal;
	    
	    @Column(name = "gender")
	    private String gender;
	    
	    
	    public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getGoal() {
			return goal;
		}

		public void setGoal(String goal) {
			this.goal = goal;
		}

		public String getProblem() {
			return problem;
		}

		public void setProblem(String problem) {
			this.problem = problem;
		}

		@Column(name = "problem")
	    private String problem;
	    
	    public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    
	    public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}
}
