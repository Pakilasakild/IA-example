package com.ia.ia_base.database.models.example;

/**
 * Studento klasė - PAVELDĖJIMO pavyzdys
 * Paveldi BasePersonEntity ir prideda studento specifines savybes
 */
public class StudentEntityExample extends BasePersonEntity {
    private String studentNumber;
    private int year;
    private double gpa;
    
    public StudentEntityExample() {
        super(); // Iškviečia tėvinės klasės konstruktorių
    }
    
    public StudentEntityExample(String name, String email, String phone,
                                String studentNumber, int year, double gpa) {
        super(name, email, phone); // Iškviečia tėvinės klasės konstruktorių
        this.studentNumber = studentNumber;
        this.year = year;
        this.gpa = gpa;
    }
    
    // Tik StudentEntityExample specifiniai getters ir setters
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

