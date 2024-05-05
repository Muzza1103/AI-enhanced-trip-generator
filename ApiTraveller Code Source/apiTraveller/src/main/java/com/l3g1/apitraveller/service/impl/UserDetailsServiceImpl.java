package com.l3g1.apitraveller.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.l3g1.apitraveller.model.User;
import com.l3g1.apitraveller.repository.UserRepository;
// Service class for loading user details by username
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Autowired instance of UserRepository for accessing user data
    @Autowired
    UserRepository userRepository;

    // Method to load user details by username
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username in the repository, or throw UsernameNotFoundException if not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Build UserDetailsImpl object from the retrieved user and return it
        return UserDetailsImpl.build(user);
    }
}