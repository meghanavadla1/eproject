package com.example.demo.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import static com.example.demo.controllers.CartController.log;

@RestController
//RestController is used for making restful web services with the help of the @RestController annotation

@RequestMapping("/api/user")
public class UserController {


	@Autowired
	// @Autowired annotation is used for dependency injection

	private UserRepository userRepository;




	@Autowired
	// @Autowired annotation is used for dependency injection

	private CartRepository cartRepository;




	@Autowired
	// @Autowired annotation is used for dependency injection
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Long id;
	private String username;






	@GetMapping("/id/{id}")
	//@GetMapping annotation maps HTTP GET requests onto specific handler methods
	public ResponseEntity<User> findById(@PathVariable Long id) {
		this.id = id;
		log.info(" User ID found -  {}", id);
		//calling info method

		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	//@GetMapping annotation maps HTTP GET requests onto specific handler methods
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		this.username = username;
		//calling info method

		log.info(" User name found -  {}", username);
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	//@PostMapping is a composed annotation that acts as a shortcut for @RequestMapping
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		log.info("Creating user with user name {}", createUserRequest.getUsername());
		//calling info method

		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		//user,username,cartuserRepository
		if (createUserRequest.getPassword().length() >= 7 && createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
			//userRepository
			userRepository.save(user);

			//calling info method

			log.info("User created successfully .User name  is {}", createUserRequest.getUsername());
			return ResponseEntity.ok(user);//return
		} else
		{
			//calling info method

			log.error("CreateUser request failure. Error with user name or password {}", createUserRequest.getUsername());
			return ResponseEntity.badRequest().build();
		}
	}
	
}
