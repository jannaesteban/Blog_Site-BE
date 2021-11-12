package com.example.demo.domain.appUser;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.security.Principal;


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
    @PreAuthorize("hasAnyAuthority('READ_ALL')")
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<Collection<User>>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }

    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('DELETE_OTHERS', 'DELETE_OWN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        return ResponseEntity.ok().body(userService.deleteUser(username));
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('READ_OWN', 'READ_ALL')")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username, Principal currentUser){
        return ResponseEntity.ok().body(userService.findByUsername(username, currentUser));
    }

    @PutMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('UPDATE_OWN', 'UPDATE_OTHERS')")
    public ResponseEntity editUserById(@RequestBody User editedUser, @PathVariable String username, Principal currentUser) throws InstanceNotFoundException {
        return ResponseEntity.ok().body(userService.editUserByUsername(editedUser, username, currentUser));
    }

}
