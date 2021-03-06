package com.example.demo.domain.appUser;


import com.example.demo.domain.exception.UserException;
import com.example.demo.domain.role.Role;
import org.springframework.security.access.AuthorizationServiceException;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.security.Principal;
import java.util.List;

public interface UserService {
    User saveUser(User user) throws InstanceAlreadyExistsException;
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    User findByUsername(String username, Principal principal) throws InstanceNotFoundException, UserException;
    List<User> getAllUsers() throws UserException;
    String deleteUser(String username, Principal principal) throws InstanceNotFoundException, UserException;
    User createUser(User newUser)throws UserException, InstanceAlreadyExistsException;
    User editUserByUsername(User editedUser, String username, Principal principal)throws InstanceNotFoundException, InstanceAlreadyExistsException, AuthorizationServiceException;

}
