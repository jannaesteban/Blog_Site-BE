package com.example.demo.domain.appUser;

import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @Override
//    This method is used for security authentication, use caution when changing this
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
//          Construct a valid set of Authorities (needs to implement Granted Authorities)
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(roles -> {
                roles.getAuthorities().forEach(authority -> {
                    authorities.add(new SimpleGrantedAuthority(authority.getName()));
                });
            });
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
    public Optional<User> findByUsername(String username, Principal currentUser){
        if(hasAuthority(username, currentUser)){
            User user = userRepository.findByUsername(username);
            if(user != null){
                return userRepository.findById(user.getId());
            }
        }
        return Optional.empty();
    }

    private boolean hasAuthority(String username, Principal user) {
        User currentUser = userRepository.findByUsername(user.getName());
        return currentUser.getUsername().equals(username) || currentUser.getRoles().contains(roleRepository.findByName("ADMIN"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.deleteById(user.getId());
            return "DELETED";
        } else return "user not found";
    }

    public String editUserByUsername(User editedUser, String username) {
        User user = userRepository.findByUsername(username);
        if (user.getId() != null) {
            return userRepository.findById(user.getId()).map(updatedUser -> {
                updatedUser.setEmail(editedUser.getEmail());
                updatedUser.setPassword(editedUser.getPassword());
                updatedUser.setUsername(editedUser.getUsername());
                return userRepository.save(updatedUser);
            }).toString();
        }else return "User not found";

    }
}
