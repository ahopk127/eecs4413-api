package org.milton.auctionservice.models; // defines package for the User class - part of auction service models layer

//mport statements for JPA annotations that allow this class to interact with a relational database.
import jakarta.persistence.Entity; //marks class as a JPA entity - means it will be mapped to a database table
import jakarta.persistence.GeneratedValue; //specifies that the value of the id field will be generated automatically
import jakarta.persistence.GenerationType; // defines strategy for generating primary key values (in this case, AUTO)
import jakarta.persistence.Id; //marks id field as the primary key of the entity

@Entity //declares user class as a JPA entity that will be mapped to a database table 
//The table name will be inferred from the class name unless otherwise specified. 
//The class itself represents a "User" entity in the system.

public class User {
    @Id //marks id field as the primary key of the entity
    @GeneratedValue(strategy = GenerationType.AUTO) //id will be automatically generated, with the strategy AUTO meaning the persistence provider will choose the most appropriate strategy (e.g., auto-increment for SQL databases).
    
    private Long id; //unique identifier for each user - primary key
    private String username; //users chosen username 
    private String password; //users password
    private String firstName; //users first name
    private String lastName; //users last name
    private String streetName; //users street name
    private String streetNumber; //users street number
    private String city; //users city
    private String country; //users country
    private String postalCode; //users postal code

    public User() { //default constructor - required by JPA to create instances of the User class via reflection. 
    } //JPA uses it to instantiate the entity before populating it with data.

    //getter and setter methods for the id field. 
    public Long getId() {
        return id; //eturns the current value of id
    }

    public void setId(Long id) {
        this.id = id; //assigns a new value to id.
    }

    //getter and setter methods for the  username field. 
    public String getUsername() {
        return username; //returns the current value of username
    }

    public void setUsername(String username) {
        this.username = username; //assigns a new value to username.
    }

    //getter and setter methods for the password field.
    public String getPassword() {
        return password; //returns the current value of password
    }

    public void setPassword(String password) {
        this.password = password; //assigns a new value to password.
    }

    //getter and setter methods for the firstName field.
    public String getFirstName() {
        return firstName; //returns the current value of firstName
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName; //assigns a new value to firstName.
    }

    //getter and setter methods for the lastName field.
    public String getLastName() {
        return lastName; // returns the current value of lastName
    }

    public void setLastName(String lastName) {
        this.lastName = lastName; // assigns a new value to lastName.
    }

    //getter and setter methods for the streetName field.
    public String getStreetName() {
        return streetName; //returns the current value of streetName
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName; // assigns a new value to streetName.
    }

    //getter and setter methods for the streetNumber field.
    public String getStreetNumber() {
        return streetNumber; //returns the current value of streetNumber
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber; //assigns a new value to streetNumber.
    }

    //getter and setter methods for the city field.
    public String getCity() {
        return city; //returns the current value of city
    }

    public void setCity(String city) {
        this.city = city; //assigns a new value to city.
    }

    //getter and setter methods for the country field.
    public String getCountry() {
        return country; //returns the current value of country
    }

    public void setCountry(String country) {
        this.country = country; //assigns a new value to country.
    }

    //getter and setter methods for the postalCode field.
    public String getPostalCode() {
        return postalCode; //returns the current value of postalCode
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode; //assigns a new value to postalCode.
    }
}
