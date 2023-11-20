package com.breiner.tesis.service.impl;
import com.breiner.tesis.dto.request.UserDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.request.UserUpdateDto;
import com.breiner.tesis.entity.Address;
import com.breiner.tesis.entity.User;
import com.breiner.tesis.enumeration.Role;
import com.breiner.tesis.exception.ResourceNotFound;
import com.breiner.tesis.exception.UserAlreadyExists;
import com.breiner.tesis.mapper.UserMapper;
import com.breiner.tesis.repository.AddressRepository;
import com.breiner.tesis.repository.UserRepository;
import com.breiner.tesis.service.INotificationService;
import com.breiner.tesis.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    public UserDto getCurrentLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedUser = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFound("user not found"));

        return userMapper.toUserDtoFromUser(loggedUser);
    }

    @Override
    public List<UserDto> getAllAdminUsers() {
        return userMapper.toUserDtoList(userRepository.findAllByRole(Role.ADMIN));
    }

    @Override
    public UserDto saveAdoptantUser(UserRegisterDto userDto, String pass) {
        String email = userDto.email();
        if(!findByEmail(email).isEmpty()){
            throw new UserAlreadyExists("User with"+ email + "already exists");
        }
        User user = userMapper.toUserFromUserRegisterDto(userDto);
        user.setRole(Role.USER);
        user.setPassword(pass);
        return userMapper.toUserDtoFromUser(userRepository.save(user));
    }

    @Override
    public UserDto saveAdminUser(UserRegisterDto userDto, String pass) {
        User user = userMapper.toUserFromUserRegisterDto(userDto);
        user.setRole(Role.ADMIN);
        user.setPassword(pass);
        return userMapper.toUserDtoFromUser(userRepository.save(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteById(Long idUser) {
        userRepository.deleteById(idUser);
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        UserDto toUpdate = getCurrentLoggedUser();
        return userRepository.findById(toUpdate.idUser()).map(
                user-> {
                    Address address = (Address) addressRepository.findById(userUpdateDto.address().idAddress()).map(
                            a -> {
                                a.setCity(userUpdateDto.address().city());
                                a.setDepartment(userUpdateDto.address().department());
                                a.setStreet(userUpdateDto.address().street());
                                a.setNeighborhood(userUpdateDto.address().neighborhood());
                                return a;
                            }

                    ).orElseThrow(() -> new ResourceNotFound("Address not found"));
                    user.setAddress(address);
                    user.setFirstName(userUpdateDto.firstName());
                    user.setLastName(userUpdateDto.lastName());
                    return userMapper.toUserDtoFromUser(userRepository.save(user));
                }
        ).orElseThrow(() -> new ResourceNotFound("User not found"));
    }


}
