package Service.impl;

import com.l3g1.apitraveller.model.User;
import com.l3g1.apitraveller.repository.UserRepository;
import com.l3g1.apitraveller.service.impl.UserDetailsImpl;
import com.l3g1.apitraveller.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//Test methods for UserDetailsServiceImpl
public class UserDetailsServiceImplTest {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserDetailsServiceImpl userDetailsServiceImpl =Mockito.mock(UserDetailsServiceImpl.class);

    User user = Mockito.mock(User.class);
    String userName = user.getUsername();
    @DisplayName("loadUserByUsername")
    @Test
    public void loadUserByUsername(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserDetails userDetailsTest = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.emptyList();
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

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

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        when(userDetailsServiceImpl.loadUserByUsername(user.getUsername())).thenReturn(userDetailsTest);
        UserDetails userTest = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
        assertEquals(user.getUsername(),userTest.getUsername());
    }

}
