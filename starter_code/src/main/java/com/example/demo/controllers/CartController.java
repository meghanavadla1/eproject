package com.example.demo.controllers;

import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;





@RestController
//RestController is used for making restful web services with the help of the @RestController annotation

@RequestMapping("/api/cart")
public class CartController {
	// It is used to map web requests onto specific handler classes and/or handler methods


	public static final Logger log;
               //creating a logger method
	static {

		log = LoggerFactory.getLogger(UserController.class);
	}




	@Autowired
	          // @Autowired annotation is used for dependency injection
	private UserRepository userRepository;


	@Autowired
	         // @Autowired annotation is used for dependency injection
	private CartRepository cartRepository;



	@Autowired
	         // @Autowired annotation is used for dependency injection
	private ItemRepository itemRepository;
	private ModifyCartRequest request;

	@PostMapping("/addToCart")
	                          //@PostMapping is a composed annotation that acts as a shortcut for @RequestMapping
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		this.request = request;
		                      //calling info method
		log.info("Request addToCart success - user name {} & item id {}", request.getUsername(), request.getItemId());
		User user;//user
		user = userRepository.findByUsername(request.getUsername());//from user repository

		if (user != null) {
			//if not empty
			Optional<Item> item = itemRepository.findById(request.getItemId());
			//get items from itemrepository
			if (item.isEmpty()) {
				//if is empty
				//calling info method
				log.error("Request addToCart failure. Error with user name {}", request.getUsername());
				//return
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			//get cart from user
			Cart cart = user.getCart();
			//get quantity
			int bound = request.getQuantity();
			//adding items present in cart of a user
			for (int i = 0; i < bound; i++) {
				cart.addItem(item.get());//add items
			}
			cartRepository.save(cart);//saving the cart
			//calling info method
			log.info("Request addToCart success - user name {} & item id {}", request.getUsername(), request.getItemId());

			ResponseEntity<Cart> ok;
			ok = ResponseEntity.ok(cart);
			return ok;//return
		} else
		{
			log.error("Request addToCart failure. Error with user name {}", request.getUsername());//log error msg

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}








	@PostMapping("/removeFromCart")
	//@PostMapping is a composed annotation that acts as a shortcut for @RequestMapping
	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		//request
		this.request = request;
		log.info("Request removeFromCart success - user name {} & item id {}", request.getUsername(), request.getItemId());
		//calling info method
		User user;
		//user
		//user repository
		user = userRepository.findByUsername(request.getUsername());
		//if the user not null
		if (user != null) {
			//get item by  id from repository
			Optional<Item> item = itemRepository.findById(request.getItemId());
			//if item is present
			if (item.isPresent()) {
				//get that cart
				Cart cart = user.getCart();
				//get quantity
				int bound = request.getQuantity();
				//remove items from cart
				for (int i = 0; i < bound; i++) {
					//cart items  remove
					cart.removeItem(item.get());
				}
				//save cart
				cartRepository.save(cart);
				//calling info method
				log.info("Request removeFromCart success - user name {} & item id {}", request.getUsername(), request.getItemId());
				ResponseEntity<Cart> ok;
				ok = ResponseEntity.ok(cart);
				return ok;//return
			} else
			{
				//error log
				log.error("Request removeFromCart failure. Error with user name {}", request.getUsername());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else
		{
			//log error
			log.error("Request removeFromCart failure. Error with user name {}", request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
		
}
