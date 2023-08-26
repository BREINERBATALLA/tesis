package com.breiner.tesis.service;

import com.breiner.tesis.entity.User;

public interface AuthenticationService {

    void registerUser(User user);

    void SingIn();

    String generatePassword(int size);
}
