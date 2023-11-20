package com.breiner.tesis.mapper;

import com.breiner.tesis.dto.request.UserDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.request.UserUpdateDto;
import com.breiner.tesis.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(source = "address", target = "address"),
            @Mapping(target = "idUser", ignore = true ),
            @Mapping(target = "authorities", ignore = true )


    })
    User toUserFromUserRegisterDto(UserRegisterDto userRegisterDto);

    UserDto toUserDtoFromUser(User user);

    @Mappings({
    @Mapping(target = "password", ignore = true ),
    @Mapping(target = "role", ignore = true ),
    @Mapping(target = "authorities", ignore = true )
    })
    User toUserFromUserDto(UserDto userDto);

    List<UserDto> toUserDtoList(List<User> userList);

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(source = "user.address", target = "address"),
            @Mapping(target = "identificationNumber", ignore = true ),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "authorities", ignore = true ),
            @Mapping(target = "email", ignore = true ),
            @Mapping(target = "idUser", ignore = true )
    })
    User toUserFromUserUpdateDto(UserUpdateDto user);
}
