package com.ia.ia_base.database.models.example;

/**
 * Kliento klasė - KOMPOZICIJOS pavyzdys
 * Turi AddressEntity objektą kaip savo dalį (kompozicija)
 */
public class CustomerEntityExample {
    private int id;
    private String name;
    private String email;
    private AddressEntity address; // KOMPOZICIJA - Customer turi Address
    
    public CustomerEntityExample() {
        this.address = new AddressEntity(); // Sukuriame tuščią adresą
    }
    
    public CustomerEntityExample(String name, String email, AddressEntity address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
    
    // Getters ir Setters
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
    
    // Kompozicijos getter ir setter
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

