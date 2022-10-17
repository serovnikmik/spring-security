package com.example.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/authenticated/**").authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");
    }

//    @Bean
//    public UserDetailsService users(){
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$qxFUGFjx3ZZldbYJcfvhUeGs6oZIMzFjpBEBzo4uFUKcyihTmLZF2")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$qxFUGFjx3ZZldbYJcfvhUeGs6oZIMzFjpBEBzo4uFUKcyihTmLZF2")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    //jdbc authentication
    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource){
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$12$qxFUGFjx3ZZldbYJcfvhUeGs6oZIMzFjpBEBzo4uFUKcyihTmLZF2")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$12$qxFUGFjx3ZZldbYJcfvhUeGs6oZIMzFjpBEBzo4uFUKcyihTmLZF2")
                .roles("ADMIN", "USER")
                .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (users.userExists(user.getUsername())){
            users.deleteUser(user.getUsername());
        }
        if (users.userExists(admin.getUsername())){
            users.deleteUser(admin.getUsername());
        }
        users.createUser(user);
        users.createUser(admin);

        return users;
    }

}
