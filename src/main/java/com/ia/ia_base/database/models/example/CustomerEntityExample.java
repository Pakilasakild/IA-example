package com.ia.ia_base.database.models.example;

/**
 * Customer class - COMPOSITION example
 * Has AddressEntity object as its part (composition)
 */
public class CustomerEntityExample {
    private int id;
    private String name;
    private String email;
    private AddressEntity address; // COMPOSITION - Customer has Address
    
    public CustomerEntityExample() {
        this.address = new AddressEntity(); // Create empty address
    }
    
    public CustomerEntityExample(String name, String email, AddressEntity address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Composition getter and setter
    public AddressEntity getAddress() {
        return address;
    }
    
    public void setAddress(AddressEntity address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return "CustomerEntityExample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}

