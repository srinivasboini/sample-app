package com.srini.demo.service;

import com.srini.demo.dto.AuthRequest;
import com.srini.demo.dto.AuthResponse;
import com.srini.demo.dto.RegisterRequest;
import com.srini.demo.entity.Role;
import com.srini.demo.entity.UserEntity;
import com.srini.demo.exception.BadRequestException;
import com.srini.demo.exception.ResourceNotFoundException;
import com.srini.demo.repository.UserRepository;
import com.srini.demo.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        log.info("Processing user registration for username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email '" + request.getEmail() + "' is already registered");
        }

        Role assignedRole = request.getRole() != null ? request.getRole() : Role.ROLE_USER;

        UserEntity user = new UserEntity(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                assignedRole
        );

        UserEntity savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return new AuthResponse(
                token,
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole(),
                jwtService.getExpirationMs()
        );
    }

    public AuthResponse login(AuthRequest request) {
        log.info("Authenticating user: {}", request.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                jwtService.getExpirationMs()
        );
    }

    @Transactional(readOnly = true)
    public UserEntity getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }
}
