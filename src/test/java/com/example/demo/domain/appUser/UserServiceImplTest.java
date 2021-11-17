package com.example.demo.domain.appUser;

import com.example.demo.domain.exception.UserException;
import com.example.demo.domain.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void saveUser() {
        User user1 = new User(UUID.fromString("a442bb4f-66cb-4ffc-84cd-52f62a521798"), "newUser",
                "newUser@gmail.com", "password", null, null, null, null,
                Set.of(roleRepository.findByName("USER")));
        User user2 = null;
        try {
            user2 = userService.saveUser(user1);
        } catch (InstanceAlreadyExistsException ignored) {
        }
        assert user2 != null;
        assertTrue(Objects.equals(user2.getUsername(), user1.getUsername()) &&
                Objects.equals(user2.getEmail(), user1.getEmail()) &&
                Objects.equals(user2.getPassword(), user1.getPassword()) &&
                Objects.equals(user2.getUserProfile(), user1.getUserProfile()) &&
                Objects.equals(user2.getBlogPost(), user1.getBlogPost()) &&
                Objects.equals(user2.getList(), user1.getList()) &&
                Objects.equals(user2.getGroups(), user1.getGroups()));
    }

    @Test
    void getUser() {
        User user1 = userRepository.findByUsername("newUser1");
        User user2 = userService.getUser("newUser1");
        assertTrue(Objects.equals(user2.getUsername(), user1.getUsername()) &&
                Objects.equals(user2.getEmail(), user1.getEmail()) &&
                Objects.equals(user2.getPassword(), user1.getPassword()) &&
                Objects.equals(user2.getUserProfile(), user1.getUserProfile()) &&
                Objects.equals(user2.getBlogPost(), user1.getBlogPost()) &&
                Objects.equals(user2.getList(), user1.getList()) &&
                Objects.equals(user2.getGroups(), user1.getGroups()));
    }

    @Test
    void findByUsername() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("newUser1");
        String username = null;
        try {
            username = userService.findByUsername("newUser1", mockPrincipal).getUsername();
        } catch (InstanceNotFoundException | UserException ignored) {
        }

        assertEquals("newUser1", username);
    }

    @Test
    void getAllUsers() {
        try {
            assertEquals(userRepository.findAll().size(), userService.getAllUsers().size());
        } catch (UserException e) {
            assert false;
        }
    }

    @Test
    void deleteUser() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("newUser1");

        int userListSize1 = userRepository.findAll().size();
        try {
            userService.deleteUser("newUser1", mockPrincipal);
        } catch (InstanceNotFoundException | UserException ignored) {
        }

        int userListSize2 = userRepository.findAll().size();
        assertEquals(userListSize1 - 1, userListSize2);
    }

    @Test
    void deleteNotExistingUser() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");

        int userListSize1 = userRepository.findAll().size();
        try {
            userService.deleteUser("user", mockPrincipal);
        } catch (InstanceNotFoundException | UserException ignored) {
        }

        int userListSize2 = userRepository.findAll().size();
        assertEquals(userListSize1, userListSize2);
    }

    @Test
    void createUser() {
        int userListSize1 = userRepository.findAll().size();
        try {
            userService.createUser(new User(UUID.fromString("cc9a9b8f-2147-4c36-b22e-d53fa63eb82e"), "newUser3",
                    "newUser@gmail.com", "password", null, null, null, null,
                    Set.of(roleRepository.findByName("USER"))));
        } catch (UserException | InstanceAlreadyExistsException ignored) {
        }
        int userListSize2 = userRepository.findAll().size();
        assertEquals(userListSize1 + 1, userListSize2);
    }

    @Test
    void editUserByUsername() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("newUser");

        User user1 = userRepository.findByUsername("newUser");
        user1.setUsername("newUser1");
        User user2 = null;
        try {
            user2 = userService.editUserByUsername(user1, "newUser", mockPrincipal);
        } catch (InstanceNotFoundException | InstanceAlreadyExistsException ignored) {
        }

        assert user2 != null;
        assertTrue(Objects.equals(user2.getUsername(), user1.getUsername()) &&
                Objects.equals(user2.getEmail(), user1.getEmail()) &&
                Objects.equals(user2.getUserProfile(), user1.getUserProfile()) &&
                Objects.equals(user2.getBlogPost(), user1.getBlogPost()) &&
                Objects.equals(user2.getList(), user1.getList()) &&
                Objects.equals(user2.getGroups(), user1.getGroups()));

    }


}