package com.example.demo.domain.appUser;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
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


    @PutMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ALL_PRIVILEGES')")
    public ResponseEntity<User> editUserById(@RequestBody User editedUser, @PathVariable UUID id) throws InstanceNotFoundException {
        return ResponseEntity.ok().body(userService.editUserInformationById(editedUser, id));
    }

}
