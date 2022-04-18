package com.example.demo.model.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

//@Entity annotation defines that a class can be mapped to a table. And that is it, it is just a marker
@Entity
//@Table annotation allows you to specify the details of the table that will be used to persist the entity in the database
@Table(name = "user")
public class User {

	@Id
	 //Specifies the primary key of an entity
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 //Provides for the specification of generation strategies for the values of primary keys

	@JsonProperty
	 //used to map property names with JSON keys during serialization and deserialization
	private long id;//id

	@JsonProperty(access = WRITE_ONLY)
	 //used to map property names with JSON keys during serialization and deserialization
	@Column(nullable = false)
	 //used for Adding the column the name in the table of a particular MySQL database
	private String password;//password
	
	@Column(nullable = false, unique = true)
	 //used for Adding the column the name in the table of a particular MySQL database
	@JsonProperty//used to map property names with JSON keys during serialization and deserialization
	private String username;//username
	
	@OneToOne(cascade = CascadeType.ALL)
	 //@OneToOne annotation is used to create the one-to-one relationship between the Student and Address entities.
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
	 //used for Adding the column the name in the table of a particular MySQL database
	@JsonIgnore
	private Cart cart;//cart

         //get password
	public String getPassword() {
		String password;
		password = this.password;
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
