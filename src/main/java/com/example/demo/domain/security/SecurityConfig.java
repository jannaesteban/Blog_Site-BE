package com.example.demo.domain.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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

     @Override
     protected void configure(HttpSecurity http) throws Exception {
         http.httpBasic().and().csrf().disable()
                 .authorizeRequests()
                 .antMatchers("/Blog-Site").permitAll()
                 .antMatchers(HttpMethod.GET,"/Blog-Site/users").hasAnyAuthority("READ_ALL")
                 .antMatchers(HttpMethod.GET, "/Blog-Site/user/**").hasAnyAuthority("READ_ALL", "READ_OWN")
                 .antMatchers(HttpMethod.PUT, "/Blog-Site/user/**").hasAnyAuthority("UPDATE_ALL", "UPDATE_OWN")
                 .antMatchers(HttpMethod.DELETE, "/Blog-Site/user/**").hasAnyAuthority("DELETE_ALL", "DELETE_OWN")
                 .antMatchers(HttpMethod.POST, "/Blog-Site/user/**").hasAnyAuthority("CREATE")
                 .and()
                 .formLogin();
     }
 }
