package com.breiner.tesis.service;

import com.breiner.tesis.dto.request.AuthUserDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.response.JwtResponseDto;
import com.breiner.tesis.dto.response.ResponseUserDto;
import com.breiner.tesis.entity.User;

public interface IAuthenticationService {

    ResponseUserDto registerUser (UserRegisterDto user);

    JwtResponseDto singIn (AuthUserDto authUserDto);

    String generateRandomPassword (int length);
}
