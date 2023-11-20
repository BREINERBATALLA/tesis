package com.breiner.tesis.service;

import com.breiner.tesis.dto.request.UserDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.request.UserUpdateDto;
import com.breiner.tesis.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserDto getCurrentLoggedUser();

    List<UserDto> getAllAdminUsers();

    UserDto saveAdoptantUser(UserRegisterDto user, String password);

    UserDto saveAdminUser(UserRegisterDto user, String password); //subscribe to a topic.. adoptant..

    Optional<User> findByEmail(String email);

    void deleteById(Long idUser);

    UserDto updateUser(UserUpdateDto user);


}
