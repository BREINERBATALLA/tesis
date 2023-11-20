package com.breiner.tesis.config.security;

import com.breiner.tesis.enumeration.Role;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.breiner.tesis.enumeration.Role.ADMIN;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity //remplaza el extends webSecurityConfig..
@RequiredArgsConstructor
//@EnableAspectJAutoProxy
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    //whiteList que no requieren autenticación(todas tienen esto)--> login.. no necesitamos token porque no lo tenemos

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                cors()
                .disable().
                csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("api/v1/auth/**") //representa nuestros path
                .permitAll() //permite todo los pattern de arriba
                .requestMatchers(POST,"/api/v1/adoption-pet/create").hasRole(ADMIN.name())
                .requestMatchers(POST,     "api/v1/admin/**").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated() //todas las demas (anyRequest) necesitan estar autenticado el user
                .and() //add new configuration
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //nueva sesion para cada peticion
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //antes del filtro UsernamePasswordFilter(defecto) el de nosotros.

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}