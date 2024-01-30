package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByUsername(String username);
	 @Query("SELECT u FROM User u WHERE u.username = ?1")
	    User getCurrentUser(String username);
	
}
