package com.example.demo.domain.appUser;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController @RequestMapping("/Blog-Site")
@RequiredArgsConstructor
public class UserController {
//    ADD YOUR ENDPOINT MAPPINGS HERE
private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> HomeTest(){
        return ResponseEntity.ok().body("Hello World");
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ALL_PRIVILEGES')")
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<Collection<User>>(userService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ALL_PRIVILEGES')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID uuid){
        return ResponseEntity.ok().body(userService.deleteUser(uuid));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable UUID id) throws InstanceNotFoundException {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ALL_PRIVILEGES') " + "|| hasRole('USER')")
    public ResponseEntity<User> editUserById(Principal currentUser, @RequestBody User editedUser, @PathVariable UUID id) throws InstanceNotFoundException {
        return ResponseEntity.ok().body(userService.editUserInformationById(currentUser, editedUser, id));
    }

}
