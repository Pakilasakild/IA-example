package com.ia.ia_base.database.models.example;

/**
 * Base class - inheritance example
 * Has common properties that StudentEntity and TeacherEntity inherit
 */
public abstract class BasePersonEntity {
    protected int id;
    protected String name;
    protected String email;
    protected String phone;
    
    public BasePersonEntity() {
    }
    
    public BasePersonEntity(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
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
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}

