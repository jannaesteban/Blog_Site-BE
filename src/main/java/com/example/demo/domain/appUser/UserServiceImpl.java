package com.example.demo.domain.appUser;

import com.example.demo.domain.authority.AuthorityRepository;
import com.example.demo.domain.exception.UserException;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final AuthorityRepository authorityRepository;


    @Override
//    This method is used for security authentication, use caution when changing this
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
//          Construct a valid set of Authorities (needs to implement Granted Authorities)
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(roles -> roles.getAuthorities().forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getName()))));
//            return a spring internal user object that contains authorities and roles
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    @Override
    public User saveUser(User user) throws InstanceAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new InstanceAlreadyExistsException("User already exists");
        } else {
            return userRepository.save(user);
        }
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsername(String username, Principal principal) throws InstanceNotFoundException, UserException {
        if (hasAuthority(username, principal, "READ_ALL")) {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new InstanceNotFoundException("User " + username + " not found.");
        }
        throw new UserException("You don't have the authority to display this user.");
    }

    private boolean hasAuthority(String username, Principal principal, String authority) {
        User currentUser = userRepository.findByUsername(principal.getName());
        if (currentUser.getUsername().equals(username) || currentUser.getRoles().contains(roleRepository.findByName("ADMIN")))
            return true;
        for (Role role : currentUser.getRoles()) {
            if (role.getAuthorities().contains(authorityRepository.findByName(authority)))
                return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() throws UserException {
        if (userRepository.findAll().isEmpty()) {
            throw new UserException("No users entries");
        }
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(String username, Principal principal) throws InstanceNotFoundException, UserException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (hasAuthority(username, principal, "DELETE_ALL")) {
                userRepository.deleteById(user.getId());
                return "User " + username + " has been deleted";
            }
            throw new UserException("You don't have the authority to delete the user " + username);
        }
        throw new InstanceNotFoundException("User not found");
    }

    @Override
    public User editUserByUsername(User editedUser, String username, Principal principal) throws InstanceNotFoundException, UserException, InstanceAlreadyExistsException {
        User currentUser = userRepository.findByUsername(principal.getName());
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new InstanceNotFoundException("User " + username + " not found");
        if (!username.equals(editedUser.getUsername()) && userRepository.findByUsername(editedUser.getUsername()) != null)
            throw new InstanceAlreadyExistsException("Username " + username + " is already taken");

        if (!hasAuthority(username, principal, "UPDATE_ALL"))
            throw new UserException("You don't have the authority to edit user " + username);

        user.setEmail(editedUser.getEmail());
        user.setPassword(editedUser.getPassword());
        user.setUsername(editedUser.getUsername());
        if (currentUser.getRoles().contains(roleRepository.findByName("ADMIN"))) {
            user.setRoles(editedUser.getRoles());
        }
        return userRepository.save(user);
    }



    @Override
    public User createUser(User newUser) throws UserException, InstanceAlreadyExistsException {
        User user = new User();
        user.setEmail(newUser.getEmail().trim());
        user.setPassword(newUser.getPassword().trim());
        user.setUsername(newUser.getUsername().trim());
        user.setRoles(Set.of(roleRepository.findByName("USER")));

        if (!(user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals(""))) {
            return saveUser(newUser);
        }
        throw new UserException("All fields are required");

    }
}
