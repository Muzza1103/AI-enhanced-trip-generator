package com.l3g1.apitraveller.service.impl;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.l3g1.apitraveller.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
// UserDetailsImpl class implements UserDetails interface provided by Spring Security
public class UserDetailsImpl implements UserDetails {

    // serialVersionUID for serializable class
    private static final long serialVersionUID = 1L;

    // Fields to represent user details
    private Long id;
    private String username;
    private String email;

    // Password field is annotated with @JsonIgnore to prevent serialization of password
    @JsonIgnore
    private String password;

    // Constructor to initialize user details
    public UserDetailsImpl(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Static method to build UserDetailsImpl object from User object
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
    }

    // Method to retrieve user's authorities, returns an empty collection for simplicity
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // Getter method for user's ID
    public Long getId() {
        return id;
    }

    // Getter method for user's email
    public String getEmail() {
        return email;
    }

    // Getter method for user's password
    @Override
    public String getPassword() {
        return password;
    }

    // Getter method for user's username
    @Override
    public String getUsername() {
        return username;
    }

    // Methods for account expiration, locking, and credentials expiration are all set to true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Method to check if user is enabled, always returns true
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Equals method to check if two UserDetailsImpl objects are equal based on their ID
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}