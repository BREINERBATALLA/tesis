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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.breiner.tesis.service.INofiticationService;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final INofiticationService notificationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseUserDto registerUser(UserRegisterDto user) {
        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setFirstName(user.firstName());
        newUser.setLastName(user.lastName());
        newUser.setIdentificationNumber(user.identificationNumber());
        String passwordGenerated = generateRandomPassword(10);
        newUser.setPassword(passwordEncoder.encode(passwordGenerated));
        newUser.setRole(Role.ADOPTER);
        userRepository.save(newUser);
        notificationService.subscribeUser(user.email());
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
        var user = userRepository.findByEmail(authUserDto.email())
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
