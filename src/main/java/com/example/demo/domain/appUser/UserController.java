package com.example.demo.domain.appUser;


import com.example.demo.domain.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import java.util.Collection;
import java.security.Principal;

/**
 * This is the Controller also known as the WebLayer
 * which is connected to the Frontend. It allows HTTP-Requests
 * such as the typical CRUD-Operations.
 */
@RestController
@RequestMapping("/Blog-Site")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //framework slf4j
    private static final Logger log = LoggerFactory.getLogger(UserController.class);



    /**
     * This method gets a message
     * when the user lands on the homepage
     * it can be accessed by anyone
     * no special authorities are required
     * @return message for homepage
     */
    @GetMapping("/")
    public ResponseEntity<String> HomeTest() {
        log.info("Homepage successfully accessed");
        return ResponseEntity.ok().body("Hello the Luca's");

    }

    /**
     * This is a GET-Endpoint through which all
     * the users can be accessed and shown but
     * only the admin has the authority
     * to list everyone, for the rest such as User or
     * Supervisor an Exception will be thrown
     * @return all Users
     */
    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('READ_ALL')")
    public ResponseEntity getAllUsers() {
        try {
            log.info("Successfully retrieved all Users");
            return new ResponseEntity<Collection<User>>(userService.getAllUsers(), HttpStatus.OK);
        }catch (UserException e){
            log.error("No user entries found, database must be empty");
            return ResponseEntity.status(200).body(e.getMessage());
        }
    }

    /**
     * This is a GET-Endpoint which allows to get a
     * certain user by his username. The Admin can access
     * anyone he likes and of course the user itself. Otherwise
     * an HTTP-Status Code is shown if the user is not permissible
     * to access a user's information
     * @param username of person you want to get the information
     * @param principal current user who's logged in
     * @return certain user called by username and his information
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('READ_OWN', 'READ_ALL')")
    public ResponseEntity getUserByUsername(@PathVariable String username, Principal principal) {
        try{
            log.info("Found user " + username);
            return ResponseEntity.ok().body(userService.findByUsername(username, principal));
        }catch (InstanceNotFoundException e){
            log.error("User not found");
            return ResponseEntity.status(404).body("User not found");
        }catch (UserException e){
            log.error("You are not authorized to get " + username + "'s information");
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }

    /**
     * This is the POST-Endpoint which allows to
     * create a new user. For this certain information's
     * are asked and again all Users are allowed to create
     * a user, no authority is needed.
     * @param newUser added to the database
     * @return newUser who was created
     */
    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody User newUser) {
        try {
            log.info("Successfully created " + newUser);
            return ResponseEntity.ok(userService.createUser(newUser));
        }catch (InstanceAlreadyExistsException e){
            log.error("Username already exists");
            return ResponseEntity.status(409).body("Username is already taken");
        }catch (UserException e){
            log.error("All fields are required!");
            return ResponseEntity.status(428).body(e.getMessage());
        }
    }

    /**
     * This is a PUT-Endpoint through which existing users can be
     * updated, for this the admin has the authority to edit anyone and
     * their role also but the user can edit his personal information
     * except his role, if the searched user does not exist an HTTP-Status Code
     * will occur otherwise a confirmation that the user has been successfully updated
     * @param editedUser the newly edited user who's been updated in the database
     * @param username the person, who's information need's to be edited
     * @param principal current user who's logged in
     * @return edited User
     * @throws InstanceNotFoundException
     */
    @PutMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('UPDATE_OWN', 'UPDATE_OTHERS')")
    public ResponseEntity editUserById(@RequestBody User editedUser, @PathVariable String username, Principal principal){
        try {
            log.info("Updated user " + editedUser + " successfully");
            return ResponseEntity.ok().body(userService.editUserByUsername(editedUser, username, principal));
        } catch (UserException e) {
            log.error("All fields are required!");
            return ResponseEntity.status(428).body(e.getMessage());
        } catch (InstanceAlreadyExistsException e) {
            log.error("Conflict, Username already taken");
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (InstanceNotFoundException e) {
            log.error("User not found, can't update");
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (AuthorizationServiceException e){
            log.error("Not authorized");
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    /**
     * This is the DELETE-Endpoint through which certain
     * users can be deleted. It checks for the aurhority
     * of the user and if he's not authorized an HTTP-Status
     * Code is shown
     * @param username person to delete
     * @param principal current user who's logged in
     * @return Confirmation that the user has been deleted
     */
    @DeleteMapping("/user/{username}")
    @PreAuthorize("hasAnyAuthority('DELETE_OTHERS', 'DELETE_OWN')")
    public ResponseEntity deleteUser(@PathVariable String username, Principal principal) {
        try {
            log.info("Successfully deleted " + username);
            return ResponseEntity.ok().body(userService.deleteUser(username, principal));
        }catch (InstanceNotFoundException e){
            log.error("User to delete not found");
            return ResponseEntity.status(404).body(e.getMessage());
        }catch (UserException e){
            log.error("Not authorized to delete user");
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }





}
