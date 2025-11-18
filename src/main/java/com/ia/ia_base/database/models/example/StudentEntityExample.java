package com.ia.ia_base.database.models.example;

/**
 * Student class - INHERITANCE example
 * Extends BasePersonEntity and adds student-specific properties
 */
public class StudentEntityExample extends BasePersonEntity {
    private String studentNumber;
    private int year;
    private double gpa;
    
    public StudentEntityExample() {
        super(); // Calls parent class constructor
    }
    
    public StudentEntityExample(String name, String email, String phone,
                                String studentNumber, int year, double gpa) {
        super(name, email, phone); // Calls parent class constructor
        this.studentNumber = studentNumber;
        this.year = year;
        this.gpa = gpa;
    }
    
    // Only StudentEntityExample specific getters and setters
    public String getStudentNumber() {
        return studentNumber;
    }
    
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    @Override
    public String toString() {
        return "StudentEntityExample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", year=" + year +
                ", gpa=" + gpa +
                '}';
    }
}

