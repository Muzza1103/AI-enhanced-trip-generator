package com.l3g1.apitraveller.controller;
import java.io.Serializable;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.l3g1.apitraveller.model.User;
import com.l3g1.apitraveller.jwt.JwtUtils;
import com.l3g1.apitraveller.repository.UserRepository;
import com.l3g1.apitraveller.request.LoginRequest;
import com.l3g1.apitraveller.request.SignupRequest;
import com.l3g1.apitraveller.response.JwtResponse;
import com.l3g1.apitraveller.response.MessageResponse;
import com.l3g1.apitraveller.service.impl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
// Serializable interface to allow objects of this class to be converted into a byte stream
public class AuthController implements Serializable{
    private static final long serialVersionUID = 1L;
    // Autowired annotation to automatically inject dependencies
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    // PostMapping annotation to handle POST requests for user authentication
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),userDetails.getUsername(),userDetails.getEmail()));
    }

    @PostMapping("/signup")
    // PostMapping annotation to handle POST requests for user registration
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: l'Username est deja prit !"));
        }
        // Check if email already exists
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Email est deja utilisée !"));
        }
        // Create a new user account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Utilisateur inscrit !"));
    }


}
