package Repository;

import com.l3g1.apitraveller.model.User;
import com.l3g1.apitraveller.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//Test methods for UserRepository
public class UserRepositoryTest {
    User user = Mockito.mock(User.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    String userName = user.getUsername();
    String email = user.getEmail();
    Long id = user.getId();
    Boolean bool = Boolean.TRUE;

    @Test
    @DisplayName("findByUsername")
    public void findByUsername(){
        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
        Optional<User>user1 = userRepository.findByUsername(userName);
        assertEquals(Optional.of(user),user1);

    }
    @Test
    @DisplayName("existsByUsername")
    public void existsByUsername(){
        when(userRepository.existsByUsername(userName)).thenReturn(bool);
        assertTrue(userRepository.existsByUsername(userName));

    }
    @Test
    @DisplayName("existsByEmail")
    public void existsByEmail(){
        when(userRepository.existsByEmail(email)).thenReturn(bool);
        assertTrue(userRepository.existsByEmail(email));

    }
    @Test
    @DisplayName("findById")
    public void findById(){
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Optional<User>user1 = userRepository.findById(id);
        assertEquals(Optional.of(user),user1);


    }
}
