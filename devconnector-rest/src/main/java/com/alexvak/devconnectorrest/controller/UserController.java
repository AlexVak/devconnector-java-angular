package com.alexvak.devconnectorrest.controller;

import com.alexvak.devconnectorrest.domain.User;
import com.alexvak.devconnectorrest.exception.DuplicateUserFoundException;
import com.alexvak.devconnectorrest.exception.MessageType;
import com.alexvak.devconnectorrest.payload.ApiResponse;
import com.alexvak.devconnectorrest.payload.LoginRequest;
import com.alexvak.devconnectorrest.payload.RegisterRequest;
import com.alexvak.devconnectorrest.security.JwtTokenProvider;
import com.alexvak.devconnectorrest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new ApiResponse(MessageType.SUCCESS, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        if (userService.isUserExist(registerRequest)) {
            throw new DuplicateUserFoundException(registerRequest);
        }

        User user = userService.createUser(registerRequest);

        return ResponseEntity.ok(new ApiResponse(MessageType.SUCCESS, String.format("User %s created", user.getName())));
    }


}
