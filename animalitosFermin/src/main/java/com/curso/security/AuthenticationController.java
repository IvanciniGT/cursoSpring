package com.curso.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    // Quien se va a encargar de hacer la AUTENTICACION
    // Se lo pido a spring.
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    // Nuestra BBDD Extenda de usuario.
    // Se la pido a Spring... Alguién la habrá configurado.
    private final UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        // Aqui tendríamos la oportunidad de meter más datos del usuario
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}

// Si usase un IAM externo... Esto no lo montaría... ni el AuthenticationRequest.
// Eso lo implementará el IAM externo
// Para pruebas guay o si quiero montar mi IAM... mi proveedor de tokens propio