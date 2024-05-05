package Service.impl;

import com.l3g1.apitraveller.service.impl.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

//Test methods for UserDetailsImpl
public class UserDetailsImplTest {
    String username ="usernameTest";
    String email = "email@Test";
    long id  =  1L;
    String password ="passwordTest";

    UserDetailsImpl userDetailsImpl = new UserDetailsImpl(id,username,email,password) ;



    @DisplayName("getAuthorities()")
    @Test
    public void getAuthorities(){
       assertTrue (userDetailsImpl.getAuthorities().isEmpty());

    }
    @DisplayName("getId()")
    @Test
    public void getId(){
        assertTrue(userDetailsImpl.getId().equals(id));
    }

    @DisplayName("getEmail()")
    @Test
    public void getEmail(){
        assertTrue(userDetailsImpl.getEmail().equals(email));
    }

    @DisplayName("getPassword()")
    @Test
    public void getPassword(){
        assertTrue(userDetailsImpl.getPassword().equals(password));
    }
    @DisplayName("isAccountNonExpired()")
    @Test
    public void isAccountNonExpired(){
        assertTrue(userDetailsImpl.isAccountNonExpired());
    }
    @DisplayName("isAccountNonLocked()")
    @Test
    public void isCredentialsNonExpired(){
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
    }
    @DisplayName("isEnabled()")
    @Test
    public void isEnabled(){
        assertTrue(userDetailsImpl.isEnabled());
    }
    @DisplayName("isAccountNonLocked()")
    @Test
    public void isAccountNonLocked(){
        assertTrue(userDetailsImpl.isAccountNonLocked());
    }
    @DisplayName("equals()")
    @Test
    public void equals(){
        assertTrue(userDetailsImpl.equals(userDetailsImpl));
    }


}
