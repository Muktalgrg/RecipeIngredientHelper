package com.rih.controller;

import com.rih.config.security.JwtUtils;
import com.rih.entity.*;
import com.rih.exception.ErrorMessage;
import com.rih.exception.ResourceNotFoundException;
import com.rih.repository.RoleRepository;
import com.rih.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item ->  "ROLE_"+ item.getAuthority().toUpperCase())
//                .map(item -> item.getAuthority().toUpperCase())
                .collect(Collectors.toList());

        LOGGER.info("User signin successful!");
//        System.out.println("token: "+jwt);



        return ResponseEntity.ok(
                JwtResponse.builder()
                        .email(userDetails.getUsername()) // email
                        .firstName(userDetails.getUser().getFirstName())
                        .lastName(userDetails.getUser().getLastName())
//                        .roles(userDetails.getUser().getRoles())
                        .roles(roles)
                        .id(userDetails.getUser().getId())
                        .token(jwt)
                        .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest){
        if(this.userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage("User already exists"));
        }
        User newUser = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(this.encoder.encode(signUpRequest.getPassword()))
                .build();

        // setting default role ROLE_USER
        Set<Role> roles = new HashSet<>();
        Role userRole = this.roleRepository.findByName("User");
        roles.add(userRole);
        newUser.setRoles(roles);

        System.out.println("new user: "+newUser);

        this.userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }
}
