package com.breiner.tesis.service.impl;


import com.breiner.tesis.config.security.JwtService;
import com.breiner.tesis.dto.request.AuthUserDto;
import com.breiner.tesis.dto.request.UserRegisterDto;
import com.breiner.tesis.dto.response.JwtResponseDto;
import com.breiner.tesis.dto.response.ResponseUserDto;
import com.breiner.tesis.entity.User;
import com.breiner.tesis.enumeration.Role;
import com.breiner.tesis.repository.UserRepository;
import com.breiner.tesis.service.IAuthenticationService;
import com.breiner.tesis.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.breiner.tesis.service.INotificationService;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final INotificationService notificationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseUserDto registerUser(UserRegisterDto user) {
        String passwordGenerated = generateRandomPassword(10);
        notificationService.subscribeUser(user.email());
        userService.saveAdoptantUser(user, passwordEncoder.encode(passwordGenerated));
        return new ResponseUserDto(passwordGenerated);
    }

    public ResponseUserDto registerAdminUser(UserRegisterDto user) {
        String passwordGenerated = generateRandomPassword(10);
        notificationService.subscribeAdmin(user.email());
        userService.saveAdminUser(user, passwordGenerated);
        return new ResponseUserDto(passwordGenerated);
    }

    @Override
    public JwtResponseDto singIn(AuthUserDto authUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authUserDto.email(), authUserDto.password()
                )
        ); //lanza exepcion si no encuentra dicho user y pass
        //both are coreect si llega acá.
        var user = userService.findByEmail(authUserDto.email())
                .orElse(null);
        var jwtToken = jwtService.generateToken(user);
        return new JwtResponseDto(jwtToken);
    }

    @Override
    public String generateRandomPassword(int length) {
        // Rango ASCII – alfanumérico (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // cada iteración del bucle elige aleatoriamente un carácter del dado
        // rango ASCII y lo agrega a la instancia `StringBuilder`

        for (int i = 0; i < length; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
