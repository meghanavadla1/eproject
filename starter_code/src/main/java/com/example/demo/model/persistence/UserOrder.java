package com.example.demo.model.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
//@Entity annotation defines that a class can be mapped to a table. And that is it, it is just a marker
@Table(name = "user_order")
// @Table annotation allows you to specify the details of the table that will be used to persist the entity in the database
public class UserOrder {
//userorder class
	@Id
	 //Specifies the primary key of an entity
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 //Provides for the specification of generation strategies for the values of primary keys

	@JsonProperty //used to map property names with JSON keys during serialization and deserialization
	@Column
	private Long id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonProperty
	  //used to map property names with JSON keys during serialization and deserialization
	@Column
    private List<Item> items;
	
	@ManyToOne
	 //@ManyToMany annotation that contains Library class object of List type
	@JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
	 //in order to add a column
	@JsonProperty //used to map property names with JSON keys during serialization and deserialization
    private User user;
	
	@JsonProperty
	 //used to map property names with JSON keys during serialization and deserialization
	@Column
	 //used for Adding the column the name in the table of a particular MySQL database
	private BigDecimal total;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

      //create from cart
	public static UserOrder createFromCart(Cart cart) {
		UserOrder order = new UserOrder();

		order.setItems(cart.getItems().stream().collect(Collectors.toList()));

		order.setTotal(cart.getTotal());

		order.setUser(cart.getUser());

		return order;
	}
	
}
