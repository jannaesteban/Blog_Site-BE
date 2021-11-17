package com.example.demo.domain.appUser;

import com.example.demo.domain.role.Role;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Table name in the databse
 */
@Entity(name="users")
//from lombok
@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class User {
    /**
     * These are all the attributes the table users has
     * in the database, the id is always
     * generated automatically
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    /**
     * Constraint makes sure that the username
     * is always unique
     */
    @NotNull
    @Column(unique=true)
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Column(name = "user_profile")
    private String userProfile;
    @Column(name = "blog_post")
    private String blogPost;
    @Column(name = "list")
    private String list;
    @Column(name = "groups")
    private String groups;

    /**
     * JoinTable called users_role, which has
     * the users_id as the foreign key and the
     * role_id as a foreign key
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;


}
