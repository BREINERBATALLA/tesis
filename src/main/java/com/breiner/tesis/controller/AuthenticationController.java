package com.breiner.tesis.controller;

import com.breiner.tesis.dto.request.AuthUserDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.response.JwtResponseDto;
import com.breiner.tesis.dto.response.ResponseUserDto;
import com.breiner.tesis.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseUserDto> register(
            @RequestBody @Valid UserRegisterDto userRegisterDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.registerUser(userRegisterDTO));
    }


    @PostMapping(path = "/sign-in")
    public ResponseEntity<JwtResponseDto> signIn(
            @RequestBody @Valid AuthUserDto authUserDto
    ) {
        return ResponseEntity.ok(authenticationService.singIn(authUserDto));
    }
}
