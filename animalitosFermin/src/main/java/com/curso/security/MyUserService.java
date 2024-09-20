package com.curso.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MyUserService {

    // Aqui configuramos nuestra BBDD de detalles adicionales del usuario.
    // Yo la tengo implementada muy cutre
    // Usando una utilidad que nos da Spring, llamada: InMemoryUserDetailsManager
    // Es genial pra pruebas... Para producción, si yo quiero implementar mi propia BBDD de detalles de usuario NO VALE.
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("password")) // Definir contraseña encriptada
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123")) // Definir contraseña encriptada
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // Configurar el PasswordEncoder para BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
// COn un proveedor externo, si tiene todos los datos que necesito. NO NECESITO NADA DE ESTO!