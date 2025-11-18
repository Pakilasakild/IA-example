package com.ia.ia_base.database.example;

/**
 * Pavyzdinė Entity klasė.
 * 
 * Ši klasė atspindi duomenų bazės lentelės struktūrą.
 */
public class ExampleEntity {
    private int id;
    private String name;
    private String description;
    
    public ExampleEntity() {
    }
    
    public ExampleEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "ExampleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

