/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enitities;

/**
 *
 * @author alaaKholi
 */
public class Borrower {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer mobile;
    private String email;
    private String address;
    private char gender;

    public Borrower() {
    }

    public Borrower(Integer id, String firstName, String lastName, Integer mobile, String email, String address, char gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.gender = gender;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Borrowers{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName +
                ", mobile=" + mobile + ", email=" + email + ", address=" + address + ", gender=" + gender + '}';
    }

 


    

   
    

}
