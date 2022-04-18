package com.example.demo.model.persistence;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
//@Entity annotation defines that a class can be mapped to a table. And that is it, it is just a marker
// @Table annotation allows you to specify the details of the table that will be used to persist the entity in the database
@Table(name = "item")
public class Item {
//class item
	@Id
	 //Specifies the primary key of an entity
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  //Provides for the specification of generation strategies for the values of primary keys

	@JsonProperty
	 //used to map property names with JSON keys during serialization and deserialization
	private Long id;
	//id
	@Column(nullable = false)
	 //used for Adding the column the name in the table of a particular MySQL database
	@JsonProperty
	 //used to map property names with JSON keys during serialization and deserialization
	private String name;
	//name
	@Column(nullable = false)
	 //used for Adding the column the name in the table of a particular MySQL database
	@JsonProperty//used to map property names with JSON keys during serialization and deserialization
	private BigDecimal price;
	//price
	@Column(nullable = false)
	//used for Adding the column the name in the table of a particular MySQL database
	@JsonProperty
			//used to map property names with JSON keys during serialization and deserialization
	private String description;
	//description



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
