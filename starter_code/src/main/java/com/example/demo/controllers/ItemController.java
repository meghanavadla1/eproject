package com.example.demo.controllers;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

import static com.example.demo.controllers.CartController.log;

@RestController
//RestController is used for making restful web services with the help of the @RestController annotation

@RequestMapping("/api/item")
// It is used to map web requests onto specific handler classes and/or handler methods

public class ItemController {





	@Autowired
	// @Autowired annotation is used for dependency injection

	private ItemRepository itemRepository;
	private String name;

	@GetMapping
	//@GetMapping annotation maps HTTP GET requests onto specific handler methods
	public ResponseEntity<List<Item>> getItems() {
		//calling info method
		log.info("Success - Getitems. Items are located");
		ResponseEntity<List<Item>> ok;
		ok = ResponseEntity.ok(itemRepository.findAll());
		return ok;//return
	}




	@GetMapping("/{id}")
	//@GetMapping annotation maps HTTP GET requests onto specific handler methods
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		//get item by id
		//calling info method
		log.info("Item ID = {}" , id);
		ResponseEntity<Item> of;
		of = ResponseEntity.of(itemRepository.findById(id));
		return of;//return
	}





	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		//get item by name
		//callng log info method
		log.info("Items name = " , name);
		List<Item> items = itemRepository.findByName(name);
		return items == null || items.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(items);
//if item is null
	}


}
