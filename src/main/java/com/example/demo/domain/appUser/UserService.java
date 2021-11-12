package com.example.demo.domain.appUser;


import com.example.demo.domain.role.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User saveUser(User user) throws InstanceAlreadyExistsException;
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    Optional<User> findByUsername(String username, Principal currentUser);
    List<User> findAll();
    String deleteUser(String username);
    String createUser(User newUser);
    ResponseEntity editUserByUsername(User editedUser, String username, Principal currentUser);

}
