package com.alexvak.devconnectorrest.service;

import com.alexvak.devconnectorrest.domain.User;
import com.alexvak.devconnectorrest.payload.RegisterRequest;

public interface UserService {

    boolean isUserExist(RegisterRequest registerRequest);

    User createUser(RegisterRequest registerRequest);
}
