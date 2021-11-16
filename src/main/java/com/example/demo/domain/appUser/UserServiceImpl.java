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

/**
 * This is the service layer, which is between the controller
 * and repository. This class implements from the Userservice and UserDetailsService
 * which contains all the logic behind each endpoint.
 */
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

    /**
     * This method returns User details:
     * roles, authority, username,email and password
     * that will be shown as soon as you display a user.
     * If the user couldn't be found it will throw an
     * exception.
     * @param username is the unique login name of the user
     * @return a user found by his username otherwise an exception
     * @throws UsernameNotFoundException
     */
    @Override
//    This method is used for security authentication, use caution when changing this
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = getUser(username);

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


    /**
     * This method checks if the user with this username already
     * exists, if not it will pass to the repository otherwise
     * it will throw an exception.
     * @param user is the user that you want to save in the db
     * @return user, that you saved
     * @throws InstanceAlreadyExistsException when username is already used
     */
    @Override
    public User saveUser(User user) throws InstanceAlreadyExistsException {
        if (getUser(user.getUsername()) != null) {
            throw new InstanceAlreadyExistsException("User already exists");
        } else {
            return userRepository.save(user);
        }
    }

    /**
     * This method passes the new role, that
     * you want to add to the repository.
     * @param role is the role you want to save
     * @return the role that has been saved in the database
     */
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * This method adds a role to the assigned user.
     * @param username is the unique login name of the user
     * @param rolename is the role you want to add to the user
     */
    @Override
    public void addRoleToUser(String username, String rolename) {
        User user =getUser(username);
        Role role = roleRepository.findByName(rolename);
        user.getRoles().add(role);
    }

    /**
     * This method gets a user by his unique username.
     * @param username is the unique login name of the user
     * @return user that was found or null
     */
    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * This method returns a user only when the logged in user
     * has the authority and the wanted user exists.
     * @param username is the unique login name of the user
     * @param principal  the current logged in user
     * @return user the user that was found by the username
     * @throws InstanceNotFoundException when the user couldn't be found
     * @throws UserException when the user doesn't have the authority to get a user
     */
    @Override
    public User findByUsername(String username, Principal principal) throws InstanceNotFoundException, UserException {
        if (hasAuthority(username, principal, "READ_ALL")) {
            User user = getUser(username);
            if (user != null) {
                return user;
            }
            throw new InstanceNotFoundException("User " + username + " not found.");
        }
        throw new UserException("You don't have the authority to display this user.");
    }

    /**
     * This methods checks if the logged in user has the authority, that
     * we asked for in the parameter or if hi is an admin.
     * @param username is the unique login name of the user
     * @param principal is the current logged in user
     * @param authority is the authority you want to check
     * @return
     */
    private boolean hasAuthority(String username, Principal principal, String authority) {
        User currentUser = getUser(principal.getName());
        if (currentUser.getUsername().equals(username) || currentUser.getRoles().contains(roleRepository.findByName("ADMIN")))
            return true;
        for (Role role : currentUser.getRoles()) {
            if (role.getAuthorities().contains(authorityRepository.findByName(authority)))
                return true;
        }
        return false;
    }

    /**
     * This methods returns all users, if there aren't any
     * users it throws an exception.
     * @return list of all users
     * @throws UserException when the database is empty.
     */
    @Override
    public List<User> getAllUsers() throws UserException {
        if (userRepository.findAll().isEmpty()) {
            throw new UserException("No users entries");
        }
        return userRepository.findAll();
    }

    /**
     * This method passes the user that you want to delete
     * to the repository, which gets deleted.
     * @param username is the unique login name of the user
     * @param principal is the current logged in user
     * @return a message that says which user has been deleted
     * @throws InstanceNotFoundException when the user couldn't be found
     * @throws UserException when the logged user doesn't have the authority
     */
    @Override
    public String deleteUser(String username, Principal principal) throws InstanceNotFoundException, UserException {
        User user = getUser(username);
        if (user != null) {
            if (hasAuthority(username, principal, "DELETE_ALL")) {
                userRepository.deleteById(user.getId());
                return "User " + username + " has been deleted";
            }
            throw new UserException("You don't have the authority to delete the user " + username);
        }
        throw new InstanceNotFoundException("User not found");
    }

    /**
     * This method edits/updates a user by his username, it
     * also checks if the user exists and if the logged in user
     * has the authority to do that.
     * @param editedUser the user, that has been edited
     * @param username the username of the user you want to edit
     * @param principal is the current logged in user
     * @return user with the edited attributes
     * @throws InstanceNotFoundException
     * @throws UserException
     * @throws InstanceAlreadyExistsException
     */
    @Override
    public User editUserByUsername(User editedUser, String username, Principal principal) throws InstanceNotFoundException, UserException, InstanceAlreadyExistsException {
        User currentUser = getUser(principal.getName());
        User user = getUser(username);
        if (user == null)
            throw new InstanceNotFoundException("User " + username + " not found");
        if (!username.equals(editedUser.getUsername()) && getUser(editedUser.getUsername()) != null)
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

    /**
     * This method creates a new user and passes it to
     * the repository.
     * @param newUser is the new user you want to add
     * @return the user that you added
     * @throws UserException when the fields are empty
     * @throws InstanceAlreadyExistsException when username already is been used
     */
    @Override
    public User createUser(User newUser) throws UserException, InstanceAlreadyExistsException {
        User user = new User();
        user.setEmail(newUser.getEmail().trim());
        user.setPassword(newUser.getPassword().trim());
        user.setUsername(newUser.getUsername().trim());
        user.setRoles(Set.of(roleRepository.findByName("USER")));

        if (!(user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals(""))) {
            if(getUser(user.getUsername()) == null){
                return saveUser(newUser);
            }
            throw new InstanceAlreadyExistsException("Username is already taken");
        }
        throw new UserException("All fields are required");

    }
}
