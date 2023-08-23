package com.example.security.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {



        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // Spring Security will evaluate the rules
            // in the order they are defined, and the first matching
            // rule will be applied.

            http
                    .csrf().disable()
                    .authorizeHttpRequests(authorizeHttpRequests ->
                            authorizeHttpRequests
                                    //permit all access to index
                                    .requestMatchers("/", "/index").permitAll()
                                    // if method is GET and path is products, anyone logged in can access
                                    .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().equals("/products"))
                                    .hasAnyRole("SUPER_ADMIN", "ADMIN", "USER")
                                    // if method is GET and addProduct, only superAdmin can do it
                                    .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().equals("/addProduct"))
                                    .hasRole("SUPER_ADMIN")
                                    // only superAdmin and Admin can post
                                    .requestMatchers(request -> request.getMethod().equals("POST") && request.getServletPath().startsWith("/deleteProduct"))
                                    .hasAnyRole("SUPER_ADMIN", "ADMIN")
                                    .anyRequest().authenticated()
                    ).formLogin()
                    .failureUrl("/failure")
                    .defaultSuccessUrl("/products");


            return http.build();
        }

// for custom login

/*
    @Controller
    public class LoginController {
        @GetMapping("/login")
        public String login() {
            return "loginTEST";
        }
    }

 */





    // defining roles
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails bob = User.withUsername("bob")
                .password(passwordEncoder.encode("pass"))
                .roles("SUPER_ADMIN")
                .build();
        UserDetails tom = User.withUsername("tom")
                .password(passwordEncoder.encode("pass"))
                .roles("ADMIN")
                .build();
        UserDetails mary = User.withUsername("mary")
                .password(passwordEncoder.encode("pass"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(bob, tom, mary);
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }








}

// without custom login
/*
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Spring Security will evaluate the rules
        // in the order they are defined, and the first matching
        // rule will be applied.

        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                //permit all access to index
                                .requestMatchers("/", "/index").permitAll()
                                // if method is GET and path is products, anyone logged in can access
                                .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().equals("/products"))
                                .hasAnyRole("SUPER_ADMIN", "ADMIN", "USER")
                                // if method is GET and addProduct, only superAdmin can do it
                                .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().equals("/addProduct"))
                                .hasRole("SUPER_ADMIN")
                                // only superAdmin and Admin can post
                                .requestMatchers(request -> request.getMethod().equals("POST") && request.getServletPath().startsWith("/deleteProduct"))
                                .hasAnyRole("SUPER_ADMIN", "ADMIN")
                                .anyRequest().authenticated()
                ).formLogin()
                .failureUrl("/failure")
                .defaultSuccessUrl("/products");


        return http.build();
    }
 */

// test

/*
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Spring Security will evaluate the rules
        // in the order they are defined, and the first matching
        // rule will be applied.

        // permit access to / and /index
        // then permit access to the servlet paths based on the assignment spec

        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                //permit all access to index
                                .requestMatchers("/", "/index").permitAll()
                                // if method is GET and path is products, anyone logged in can access
                                .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().equals("/products"))
                                .hasAnyRole("SUPER_ADMIN", "ADMIN", "USER")
                                // if method is GET and addProduct, only superAdmin can do it
                                .requestMatchers(request -> request.getMethod().equals("GET") && request.getServletPath().equals("/addProduct"))
                                .hasRole("SUPER_ADMIN")
                                // only superAdmin and Admin can post
                                .requestMatchers(request -> request.getMethod().equals("POST") && request.getServletPath().startsWith("/deleteProduct"))
                                .hasAnyRole("SUPER_ADMIN", "ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/login") // Use custom login page (overrides custom login)
                .failureUrl("/login?error") // Redirect to custom login page with error parameter
                .defaultSuccessUrl("/products")
                .permitAll() // Allow access to the login page
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll(); // all can access this as well

        return http.build();
    }
 */