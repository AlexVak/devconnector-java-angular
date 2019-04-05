package com.alexvak.devconnectorrest.service.impl;

import com.alexvak.devconnectorrest.domain.User;
import com.alexvak.devconnectorrest.payload.RegisterRequest;
import com.alexvak.devconnectorrest.repository.UserRepository;
import com.alexvak.devconnectorrest.service.UserService;
import com.timgroup.jgravatar.Gravatar;
import com.timgroup.jgravatar.GravatarDefaultImage;
import com.timgroup.jgravatar.GravatarRating;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isUserExist(RegisterRequest registerRequest) {
        return userRepository.findByEmail(registerRequest.getEmail()).isPresent();
    }

    @Override
    public User createUser(RegisterRequest registerRequest) {
        Gravatar gravatar = new Gravatar(200, GravatarRating.PARENTAL_GUIDANCE_SUGGESTED, GravatarDefaultImage.MYSTERY_MAN);
        User user = new User(registerRequest.getName(), registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()), gravatar.getUrl(registerRequest.getEmail()));
        return userRepository.save(user);
    }
}
