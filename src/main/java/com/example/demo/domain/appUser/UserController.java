package com.example.demo.domain.appUser;


import com.example.demo.domain.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.security.Principal;


@RestController
@RequestMapping("/Blog-Site")
@RequiredArgsConstructor
public class UserController {
    //    ADD YOUR ENDPOINT MAPPINGS HERE
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> HomeTest() {
        return ResponseEntity.ok().body("Hello World");
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('READ_ALL')")
    public ResponseEntity getAllUsers() {
        try {
            return new ResponseEntity<Collection<User>>(userService.getAllUsers(), HttpStatus.OK);
        }catch (UserException e){
            return ResponseEntity.status(200).body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('READ_OWN', 'READ_ALL')")
    public ResponseEntity getUserByUsername(@PathVariable String username, Principal currentUser) {
        try{
            return ResponseEntity.ok().body(userService.findByUsername(username, currentUser));
        }catch (InstanceNotFoundException e){
            return ResponseEntity.status(404).body("User not found");
        }catch (UserException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody User newUser) {
        try {
            return ResponseEntity.ok(userService.createUser(newUser));
        }catch (InstanceAlreadyExistsException e){
            return ResponseEntity.status(409).body("Username is already taken");
        }catch (UserException e){
            return ResponseEntity.status(428).body(e.getMessage());
        }
    }

    @PutMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('UPDATE_OWN', 'UPDATE_OTHERS')")
    public ResponseEntity editUserById(@RequestBody User editedUser, @PathVariable String username, Principal currentUser) throws InstanceNotFoundException {
        try {
            return ResponseEntity.ok().body(userService.editUserByUsername(editedUser, username, currentUser));
        } catch (UserException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (InstanceAlreadyExistsException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (InstanceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('DELETE_OTHERS', 'DELETE_OWN')")
    public ResponseEntity deleteUser(@PathVariable String username, Principal currentUser) {
        try {
            return ResponseEntity.ok().body(userService.deleteUser(username, currentUser));
        }catch (InstanceNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }catch (UserException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }





}
