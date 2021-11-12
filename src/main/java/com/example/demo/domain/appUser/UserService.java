package com.example.demo.domain.appUser;


import com.example.demo.domain.exception.UserException;
import com.example.demo.domain.role.Role;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.security.Principal;
import java.util.List;

public interface UserService {
    User saveUser(User user) throws InstanceAlreadyExistsException;
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    User findByUsername(String username, Principal currentUser) throws InstanceNotFoundException, UserException;
    List<User> getAllUsers() throws UserException;
    String deleteUser(String username) throws InstanceNotFoundException;
    User createUser(User newUser)throws UserException, InstanceAlreadyExistsException;
    User editUserByUsername(User editedUser, String username, Principal currentUser)throws InstanceNotFoundException,UserException, InstanceAlreadyExistsException;

}
