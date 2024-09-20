package com.curso.security;

import lombok.Data;

/*
   UN TRISTE DTO...
   Que lleva el nombre de un usuario y su contraseña
   Esto lo debe mandar un cliente para autenticarse
   Lo mandará a un endpoint (Una ruta URL) mediante post... como JSON
   POST: http://miservidor/api/v1/auth
   {
        "username": "usuario",
        "password": "misuperpassword"
   }
*/
@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
