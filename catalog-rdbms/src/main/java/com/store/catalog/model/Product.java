package com.store.catalog.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="T_PRODUCT")
@Access(AccessType.FIELD)
public  class Product implements AbstractBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6699161208358545698L;

	// ======================================
    // =             Attributes             =
    // ======================================
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,
			generator = "product_seq_generator")
	@SequenceGenerator(name="product_seq_generator",
			initialValue = 1,
			allocationSize = 1,
			sequenceName = "product_seq")
	@Column(name="id")
    private Long id;

	@Column(name="name")
    private String name;

	@Column(name="description")
    private String description;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="category_fk",
			nullable=false)
    private Category category;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy="product",
			cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<Item>();
    
 

    // ======================================
    // =            Constructors            =
    // ======================================
    public Product() {
    }

    public Product(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
    	this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
    	this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
    	this.name = name;
    }


    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

    public Set<Item> getItems() {
        return items;
    }   
    
	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id) .append(name).append(description).hashCode();
    }


}
