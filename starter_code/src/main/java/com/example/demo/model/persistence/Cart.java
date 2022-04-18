package com.example.demo.model.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
//@Entity annotation defines that a class can be mapped to a table. And that is it, it is just a marker
@Table(name = "cart")
// @Table annotation allows you to specify the details of the table that will be used to persist the entity in the database
public class Cart {

	//cart class


	@Id
	//Specifies the primary key of an entity
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//Provides for the specification of generation strategies for the values of primary keys
	@JsonProperty
	//used to map property names with JSON keys during serialization and deserialization
	@Column//used for Adding the column the name in the table of a particular MySQL database
	private Long id;//id


	@ManyToMany
	//@ManyToMany annotation that contains Library class object of List type
	@JsonProperty//used to map property names with JSON keys during serialization and deserialization
	@Column
	//used for Adding the column the name in the table of a particular MySQL database
    private List<Item> items;//items
	
	@OneToOne(mappedBy = "cart")
	//@OneToOne annotation is used to create the one-to-one relationship between the Student and Address entities.
	@JsonProperty
	//used to map property names with JSON keys during serialization and deserialization
    private User user;//user



	@Column
	//used for Adding the column the name in the table of a particular MySQL database
	@JsonProperty
	//used to map property names with JSON keys during serialization and deserialization
	private BigDecimal total;//total
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}


	//add items
	public void addItem(Item item) {
		if(items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
		if(total == null) {
			total = new BigDecimal(0);
		}
		total = total.add(item.getPrice());
	}





	//remove items
	public void removeItem(Item item) {
		if(items == null) {
			items = new ArrayList<>();
		}
		items.remove(item);
		if(total == null) {
			total = new BigDecimal(0);
		}
		total = total.subtract(item.getPrice());
	}
}
