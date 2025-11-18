package com.ia.ia_base.database.models.example;

/**
 * Address class - COMPOSITION example
 * Used as part of other classes (e.g., CustomerEntity)
 */
public class AddressEntity {
    private String street;
    private String city;
    private String postalCode;
    private String country;
    
    public AddressEntity() {
    }
    
    public AddressEntity(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    // Getters and Setters
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Override
    public String toString() {
        return street + ", " + city + ", " + postalCode + ", " + country;
    }
}

