package com.curso.emails.service;

import org.springframework.stereotype.Service;

@Service
public class EmailsServiceDummy implements EmailsService {

    public void enviarEmail(String destinatario, String asunto, String cuerpo){}

}
