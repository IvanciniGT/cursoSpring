package com.curso.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity                                          // Esta es la que activa la seguridad en SPRING para peticiones web
                                                            // Al hacerlo, Spring buscará un SecurityFilterChain... que hayamos configurado
                                                            // Para aplicarlo
                                                            // COÑO !!! que suerte que aquñi mismo lo hemos definido!
//@EnableGlobalMethodSecurity(prePostEnabled = true)          // Es la que le indica a Spring que lo controladores, en sus mmappings (GET POST...
                                                            // Pueden usar anotaciones: PRE Y POST
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true) // Tambien poder usar anotaciones RolesAllowed
public class SecurityConfig {

    // Definir la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MiFiltro jwtRequestFilter) throws Exception {
        http.csrf().disable()
                .authorizeRequests()                                // Debes aplicar autorizacion a las peticiones
                .anyRequest().permitAll()                           // POR DEFECTO, DEJA ENTRAR A TODO
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // NO QUIERO SESIONES DE USUARIO

        // Agregar el filtro JWT antes de la autenticación de username y password
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Configurar el AuthenticationManager para que use el userDetailsService y el passwordEncoder
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService) // Asignar el userDetailsService para la autenticación
                .passwordEncoder(passwordEncoder) // Asignar el PasswordEncoder para verificar las contraseñas
                .and()
                .build();
    }
}

// Si yo usase un proveedor externo de Identidades, el AuthenticationManager no lo monto...