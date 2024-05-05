package com.l3g1.apitraveller.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
// Serializable interface to allow objects of this class to be converted into a byte stream
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @ElementCollection
    @Column(length = 16000)
    private List<String> suggestions = new ArrayList<>();

    @ElementCollection
    @Column(length = 16000)
    private List<String> tripSuggestions = new ArrayList<>();

    // Constructors, Getters, and Setters

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public void addDestSuggestions(String destSuggestion){
        this.suggestions.add(destSuggestion);
    }

    public List<String> getTripSuggestions() {
        return tripSuggestions;
    }

    public void setTripSuggestions(List<String> tripSuggestions) {
        this.tripSuggestions = tripSuggestions;
    }

    public void addTripSuggestions(String tripSuggestion){
        this.tripSuggestions.add(tripSuggestion);
    }
}