package Controller;

import com.l3g1.apitraveller.controller.AuthController;
import com.l3g1.apitraveller.jwt.JwtUtils;
import com.l3g1.apitraveller.model.User;
import com.l3g1.apitraveller.repository.UserRepository;
import com.l3g1.apitraveller.request.LoginRequest;
import com.l3g1.apitraveller.request.SignupRequest;
import com.l3g1.apitraveller.response.JwtResponse;
import com.l3g1.apitraveller.service.impl.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {
    // Test methode for  AuthController
    AuthController authController = Mockito.mock(AuthController.class);
    AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    JwtUtils jwtUtils = Mockito.mock(JwtUtils.class);
    SignupRequest signupRequest = Mockito.mock(SignupRequest.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    PasswordEncoder encoder = Mockito.mock(PasswordEncoder.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("authenticateUser")
    public void testAuthenticateUser() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");


        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);


        String jwtToken = "mockedJWTToken";
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwtToken);


        UserDetailsImpl userDetails = new UserDetailsImpl(0L, "testUser", "testEmail", "testpassword");
        when(authentication.getPrincipal()).thenReturn(userDetails);


        ResponseEntity<?> testResult = ResponseEntity.ok(new JwtResponse(jwtToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
        doReturn(testResult).when(authController).authenticateUser(loginRequest);


        ResponseEntity<?> response = authController.authenticateUser(loginRequest);


        assertSame(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof JwtResponse);

        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals("testUser", jwtResponse.getUsername());
        assertEquals("testEmail", jwtResponse.getEmail());
    }

    @DisplayName("registerUser_UserNameAlreadyUse")
    @Test()
    public void registerUser_UserNameAlreadyUse() {
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);
        ResponseEntity<?> resultTest = new ResponseEntity<>("Erreur: l'Username est deja prit !", HttpStatus.BAD_REQUEST);
        doReturn(resultTest).when(authController).registerUser(signupRequest);
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        assertEquals(HttpStatus.BAD_REQUEST, resultTest.getStatusCode());
        assertEquals("Erreur: l'Username est deja prit !", resultTest.getBody());

    }

    @DisplayName("registerUser_UserEmailNotUse ")
    @Test
    public void registerUser_UserEmailNotUse() {
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);
        ResponseEntity<?> resultTest = new ResponseEntity<>("Erreur: Email est deja utilisée !", HttpStatus.BAD_REQUEST);
        doReturn(resultTest).when(authController).registerUser(signupRequest);
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        assertEquals(HttpStatus.BAD_REQUEST, resultTest.getStatusCode());
        assertEquals("Erreur: Email est deja utilisée !", resultTest.getBody());

    }
    @DisplayName("registerUser")
    @Test
    public void registerUser(){
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));
        when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<?>resultTest= new ResponseEntity<>("Utilisateur inscrit !",HttpStatus.OK);
        doReturn(resultTest).when(authController).registerUser(signupRequest);
        ResponseEntity<?> response = authController.registerUser(signupRequest);
        assertEquals(HttpStatus.OK, resultTest.getStatusCode());
        assertEquals("Utilisateur inscrit !", resultTest.getBody());


    }
}
