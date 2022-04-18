package com.example.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

import static com.example.demo.controllers.CartController.log;

@RestController
//RestController is used for making restful web services with the help of the @RestController annotation

@RequestMapping("/api/order")
public class OrderController {


	@Autowired
	// @Autowired annotation is used for dependency injection
	private UserRepository userRepository;



	@Autowired
	// @Autowired annotation is used for dependency injection
	private OrderRepository orderRepository;
	private String username;





	@PostMapping("/submit/{username}")
	//@PostMapping is a composed annotation that acts as a shortcut for @RequestMapping
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		ResponseEntity<UserOrder> result;
		this.username = username;
		log.info("Request submit - order success,Username{}", username);
		//calling info method
          //user
		User user;
		user = userRepository.findByUsername(username);
		//userRepository
		if (user != null) {
			//if not null
			UserOrder order = UserOrder.createFromCart(user.getCart());//get user order from cart
			orderRepository.save(order);
			//calling info method
            //username
			log.info("Request submit - order success,Username{}", username);
			ResponseEntity<UserOrder> ok;
			ok = ResponseEntity.ok(order);
			result = ok;
		} else
		{
			log.error("Request submit - order failure. Error with user name {}", username);
			result = ResponseEntity.notFound().build();
		}
		//return result
		return result;
	}









	@GetMapping("/history/{username}")
	//@GetMapping annotation maps HTTP GET requests onto specific handler methods
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		//calling info method
		//username
		this.username = username;
         //log method
		log.info("Request Get-Order success.Order history found for Username{}", username);
		User user = userRepository.findByUsername(username);
		//userRepository
		if (user != null) {//calling info method
           //user not nll
			log.info("Request Get-Order success.Order history found for Username{}", username);
			ResponseEntity<List<UserOrder>> ok;
			ok = ResponseEntity.ok(orderRepository.findByUser(user));
			return ok;
		} else
		{
			log.info("Request Get-Order failure.Order history cannot be found for Username{}", username);
			return ResponseEntity.notFound().build();//return
		}
	}
}
