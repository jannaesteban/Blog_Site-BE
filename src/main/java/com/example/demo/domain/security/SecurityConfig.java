package com.example.demo.domain.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
 @EnableWebSecurity @RequiredArgsConstructor @EnableGlobalMethodSecurity(prePostEnabled = true)

 public class SecurityConfig extends WebSecurityConfigurerAdapter {

     private final UserDetailsService userDetailsService;
     private final PasswordEncoder passwordEncoder;


     @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

                 auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
     }

<<<<<<< HEAD
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable()
                .authorizeRequests().anyRequest().authenticated();
//                .antMatchers("/**").permitAll()
//                .antMatchers("/api/*").permitAll()

    }
=======
     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.httpBasic().and()
                 .authorizeRequests()
                 .antMatchers("/**").hasAuthority("ALL_PRIVILEGES")
                 .antMatchers("/Blog-Site/users/{id}").permitAll()
                 .and()
                 .formLogin();
     }
>>>>>>> develop
 }
