package com.auth.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.entity.Users;
import com.auth.repo.UserRepository;
import com.auth.security.JwtUtil;
import com.auth.security.UserDetailsServiceImpl;

@CrossOrigin(origins ="http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signup")
    public String signup(@RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/signin")
    public String signin(@RequestBody Users user) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        return token;
    }
}
