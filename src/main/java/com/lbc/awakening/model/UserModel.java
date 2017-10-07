package com.lbc.awakening.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UserModel implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonIgnore
    private transient String emailRegex = "^[a-zA-Z1-9_.]+@[a-zA-Z]+\\.[a-zA-Z]{1,5}$";

    @JsonIgnore
    private transient String nameRegex = "^([a-zA-Z]{3,}\\ ?)+$";

    private String firstName;
    private String lastName;
    private int gold;
    private double gems;
    private int userType; // normal,premium,etc
    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VillageModel> villages = new ArrayList<>();

    public List<VillageModel> getVillages() {
        return villages;
    }

    public void setVillages(List<VillageModel> villages) {
        this.villages = villages;
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public double getGems() {
        return gems;
    }

    public void setGems(double gems) {
        this.gems = gems;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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


    private boolean isPasswordValid() {
        return this.password != null && this.password.matches("^.*[0-9].*$")//at least one number
                && this.password.matches("^.*[A-Z].*$")//at least one Capital character
                && this.password.matches("^.{6,}$");
    }

    public boolean isValid() {

        if (this.getEmail().matches(emailRegex) && this.firstName.matches(nameRegex) && this.lastName.matches(nameRegex)
                && isPasswordValid()) {
            return true;
        }
        return false;
    }
}
