//package com.example.inventory.model;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
//import lombok.Data;

//@Entity //Αυτόματη αποήκευση σε SQL
//@Data // Δημιουργεί αυτόματα Getters και Setters
public class Product {
    //Εδώ δημιουργεί μία αυτόματη ένα ID και μία Value
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private String category;

    public Product(Long id, String name, int quantity, double price, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public Long getId () {
        return id;
    } 

    public void setId(long newID) {
        this.id = newID;
    }

    public String getName () {
        return name;
    } 

    public void setName(String newName) {
        this.name = newName;
    }

    public Integer getQuantity () {
        return quantity;
    } 

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public Double getPrice () {
        return price;
    } 

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public String getCategory () {
        return category;
    } 

    public void setCategory(String newCategory) {
        this.category = newCategory;
    }
}