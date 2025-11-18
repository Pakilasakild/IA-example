# Modelių kūrimas, paveldėjimas ir kompozicija

Šiame dokumente paaiškinama, kaip kurti modelius (Entity klasės), naudoti paveldėjimą (inheritance) ir kompoziciją (composition) IB HL Computer Science IA projektuose.

## 1. Modelių (Entity) kūrimas

Modelis (Entity) yra klasė, kuri atspindi duomenų bazės lentelės struktūrą. Kiekviena lentelė turi savo Entity klasę.

### Paprasčiausias pavyzdys

Tarkime, turime lentelę `students`:
```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    age INT
);
```

**StudentEntity.java:**
```java
package com.ia.ia_base.database.models;

/**
 * Studento modelis (Entity klasė)
 * Atspindi 'students' lentelės struktūrą
 */
public class StudentEntity {
    // Privatūs laukai - atitinka lentelės stulpelius
    private int id;
    private String name;
    private String email;
    private int age;
    
    // Konstruktoriai
    public StudentEntity() {
        // Tuščias konstruktorius - reikalingas DAO
    }
    
    public StudentEntity(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // Getters ir Setters (reikalingi visiems laukams)
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
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    // toString() metodas - naudingas debuginimui
    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
```

### StudentDAO.java (darbui su duomenų baze)

```java
package com.ia.ia_base.database;

import com.ia.ia_base.database.models.StudentEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDAO extends BaseDAO<StudentEntity> {
    
    public List<StudentEntity> findAll() throws SQLException {
        return executeQuery("SELECT * FROM students");
    }
    
    public StudentEntity findById(int id) throws SQLException {
        List<StudentEntity> results = executeQuery("SELECT * FROM students WHERE id = ?", id);
        return results.isEmpty() ? null : results.get(0);
    }
    
    public int create(StudentEntity student) throws SQLException {
        String sql = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";
        return executeUpdate(sql, student.getName(), student.getEmail(), student.getAge());
    }
    
    @Override
    protected StudentEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        StudentEntity student = new StudentEntity();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setAge(rs.getInt("age"));
        return student;
    }
}
```

---

## 2. Paveldėjimas (Inheritance)

Paveldėjimas naudojamas, kai turime susijusias klases, kurios turi bendrų savybių.

### Pavyzdys: Žmonės sistema

**BaseEntity.java** (bazinė klasė):
```java
package com.ia.ia_base.database.models;

/**
 * Bazinė klasė visoms žmonių klasėms
 * Turi bendras savybes, kurias paveldi kitos klasės
 */
public abstract class BasePersonEntity {
    protected int id;
    protected String name;
    protected String email;
    protected String phone;
    
    // Konstruktoriai
    public BasePersonEntity() {}
    
    public BasePersonEntity(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters ir Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
```

**StudentEntity.java** (paveldi BasePersonEntity):
```java
package com.ia.ia_base.database.models;

/**
 * Studento klasė - paveldi BasePersonEntity
 * Turi papildomas studento specifines savybes
 */
public class StudentEntity extends BasePersonEntity {
    private String studentNumber;
    private int year;
    private double gpa;
    
    public StudentEntity() {
        super(); // Iškviečia tėvinės klasės konstruktorių
    }
    
    public StudentEntity(String name, String email, String phone, 
                         String studentNumber, int year, double gpa) {
        super(name, email, phone); // Iškviečia tėvinės klasės konstruktorių
        this.studentNumber = studentNumber;
        this.year = year;
        this.gpa = gpa;
    }
    
    // Tik StudentEntity specifiniai getters ir setters
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    
    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", year=" + year +
                ", gpa=" + gpa +
                '}';
    }
}
```

**TeacherEntity.java** (taip pat paveldi BasePersonEntity):
```java
package com.ia.ia_base.database.models;

/**
 * Mokytojo klasė - paveldi BasePersonEntity
 * Turi mokytojo specifines savybes
 */
public class TeacherEntity extends BasePersonEntity {
    private String employeeId;
    private String department;
    private double salary;
    
    public TeacherEntity() {
        super();
    }
    
    public TeacherEntity(String name, String email, String phone,
                        String employeeId, String department, double salary) {
        super(name, email, phone);
        this.employeeId = employeeId;
        this.department = department;
        this.salary = salary;
    }
    
    // Tik TeacherEntity specifiniai getters ir setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}
```

### Duomenų bazės struktūra su paveldėjimu

**students lentelė:**
```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    student_number VARCHAR(50) UNIQUE,
    year INT,
    gpa DECIMAL(3,2)
);
```

**teachers lentelė:**
```sql
CREATE TABLE teachers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    employee_id VARCHAR(50) UNIQUE,
    department VARCHAR(100),
    salary DECIMAL(10,2)
);
```

---

## 3. Kompozicija (Composition)

Kompozicija naudojama, kai viena klasė turi kitos klasės objektą kaip savo dalį.

### Pavyzdys: Užsakymų sistema

**AddressEntity.java** (kompozicijos dalis):
```java
package com.ia.ia_base.database.models;

/**
 * Adreso klasė - naudojama kompozicijoje
 */
public class AddressEntity {
    private String street;
    private String city;
    private String postalCode;
    private String country;
    
    public AddressEntity() {}
    
    public AddressEntity(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    // Getters ir Setters
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    @Override
    public String toString() {
        return street + ", " + city + ", " + postalCode + ", " + country;
    }
}
```

**CustomerEntity.java** (turi AddressEntity kaip kompoziciją):
```java
package com.ia.ia_base.database.models;

/**
 * Kliento klasė - naudoja kompoziciją su AddressEntity
 */
public class CustomerEntity {
    private int id;
    private String name;
    private String email;
    private AddressEntity address; // Kompozicija - Customer turi Address
    
    public CustomerEntity() {
        this.address = new AddressEntity(); // Sukuriame tuščią adresą
    }
    
    public CustomerEntity(String name, String email, AddressEntity address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
    
    // Getters ir Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // Kompozicijos getter ir setter
    public AddressEntity getAddress() { return address; }
    public void setAddress(AddressEntity address) { this.address = address; }
    
    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
```

### Duomenų bazės struktūra su kompozicija

**customers lentelė:**
```sql
CREATE TABLE customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    street VARCHAR(100),
    city VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100)
);
```

**CustomerDAO.java** (su kompozicija):
```java
package com.ia.ia_base.database;

import com.ia.ia_base.database.models.CustomerEntity;
import com.ia.ia_base.database.models.AddressEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO extends BaseDAO<CustomerEntity> {
    
    public List<CustomerEntity> findAll() throws SQLException {
        return executeQuery("SELECT * FROM customers");
    }
    
    public int create(CustomerEntity customer) throws SQLException {
        String sql = "INSERT INTO customers (name, email, street, city, postal_code, country) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        AddressEntity addr = customer.getAddress();
        return executeUpdate(sql, 
            customer.getName(), 
            customer.getEmail(),
            addr.getStreet(),
            addr.getCity(),
            addr.getPostalCode(),
            addr.getCountry()
        );
    }
    
    @Override
    protected CustomerEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        
        // Sukuriame AddressEntity objektą iš duomenų bazės
        AddressEntity address = new AddressEntity();
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setPostalCode(rs.getString("postal_code"));
        address.setCountry(rs.getString("country"));
        
        customer.setAddress(address);
        return customer;
    }
}
```

---

## 4. Sudėtingesnis pavyzdys: Paveldėjimas + Kompozicija

**ProductEntity.java** (bazinė klasė):
```java
package com.ia.ia_base.database.models;

public abstract class ProductEntity {
    protected int id;
    protected String name;
    protected double price;
    protected String description;
    
    // Getters, setters, konstruktoriai...
}
```

**BookEntity.java** (paveldi ProductEntity + turi kompoziciją):
```java
package com.ia.ia_base.database.models;

public class BookEntity extends ProductEntity {
    private String isbn;
    private String author;
    private PublisherEntity publisher; // Kompozicija
    
    // Konstruktoriai, getters, setters...
}
```

**PublisherEntity.java**:
```java
package com.ia.ia_base.database.models;

public class PublisherEntity {
    private String name;
    private String country;
    // ...
}
```

---

## 5. Naudojimas controlleryje

```java
package com.ia.ia_base.controllers;

import com.ia.ia_base.database.StudentDAO;
import com.ia.ia_base.database.models.StudentEntity;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class StudentController extends BaseController {
    private StudentDAO studentDAO = new StudentDAO();
    
    @FXML
    private TableView<StudentEntity> tableView;
    
    @FXML
    private void loadStudents() {
        try {
            List<StudentEntity> students = studentDAO.findAll();
            // Užkrauname į TableView
            tableView.getItems().clear();
            tableView.getItems().addAll(students);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Pagrindinės taisyklės

1. **Modelis = Lentelė**: Kiekviena lentelė turi savo Entity klasę
2. **Paveldėjimas**: Naudokite, kai klasės turi bendrų savybių
3. **Kompozicija**: Naudokite, kai viena klasė turi kitos klasės objektą
4. **Getters/Setters**: Visada sukurkite visiems laukams
5. **toString()**: Naudingas debuginimui
6. **DAO klasė**: Kiekvienam Entity turi būti atitinkama DAO klasė

## Kada naudoti ką?

- **Paveldėjimas**: Kai turite "yra" santykį (Student IS A Person)
- **Kompozicija**: Kai turite "turi" santykį (Customer HAS AN Address)
- **Paprastas modelis**: Kai nereikia nei paveldėjimo, nei kompozicijos

