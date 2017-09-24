package com.lbc.awakening.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class UserModel implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @JsonIgnore
    private transient String emailRegex = "^[a-zA-Z1-9_.]+@[a-zA-Z]+\\.[a-zA-Z]{1,5}$";

    @JsonIgnore
    private transient String nameRegex = "^([a-zA-Z]{3,}\\ ?)+$";

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailRegex() {
        return emailRegex;
    }

    public void setEmailRegex(String emailRegex) {
        this.emailRegex = emailRegex;
    }

    public String getNameRegex() {
        return nameRegex;
    }

    public void setNameRegex(String nameRegex) {
        this.nameRegex = nameRegex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private boolean isPasswordValid(){
        return this.password!=null && this.password.matches("^.*[0-9].*$")//at least one number
                && this.password.matches("^.*[A-Z].*$")//at least one Capital character
                && this.password.matches("^.{6,}$");
    }

    public boolean isValid(){

        if(this.getEmail().matches(emailRegex)&&this.firstName.matches(nameRegex)&&this.lastName.matches(nameRegex)
                &&isPasswordValid()){
            return true;
        }
        return false;
    }
}
