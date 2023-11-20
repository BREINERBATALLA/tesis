package com.breiner.tesis.controller;

import com.breiner.tesis.dto.request.UserDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.request.UserUpdateDto;
import com.breiner.tesis.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public ResponseEntity<List<UserDto>> getAllAdminUsers() {
        List<UserDto> adminUsers = userService.getAllAdminUsers();
        return ResponseEntity.ok(adminUsers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> saveAdminUser(@RequestBody UserRegisterDto userDto, @RequestParam String password) {
        UserDto savedAdminUser = userService.saveAdminUser(userDto, password);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdminUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteById(@PathVariable Long idUser) {
        userService.deleteById(idUser);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserUpdateDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto);
        return ResponseEntity.ok(updatedUser);
    }
}
