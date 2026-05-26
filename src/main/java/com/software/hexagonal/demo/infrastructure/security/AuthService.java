package com.software.hexagonal.demo.infrastructure.security;

import com.software.hexagonal.demo.infrastructure.input.rest.dto.AuthLoginRequest;
import com.software.hexagonal.demo.infrastructure.input.rest.dto.AuthRegisterRequest;
import com.software.hexagonal.demo.infrastructure.input.rest.dto.AuthResponse;
import com.software.hexagonal.demo.infrastructure.output.jpa.entity.AuthUserEntity;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IAuthUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IAuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUserDetailsService authUserDetailsService;
    private final JwtService jwtService;

    public AuthService(IAuthUserRepository authUserRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       AuthUserDetailsService authUserDetailsService,
                       JwtService jwtService) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.authUserDetailsService = authUserDetailsService;
        this.jwtService = jwtService;
    }

    public AuthResponse register(AuthRegisterRequest request) {
        if (authUserRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        AuthUserEntity user = new AuthUserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        authUserRepository.save(user);

        UserDetails userDetails = authUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = authUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
