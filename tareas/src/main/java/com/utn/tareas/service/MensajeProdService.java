package com.utn.tareas.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
@Profile("prod")
public class MensajeProdService implements MensajeService{
    @Override
    public void mostrarBienvenida() {
        System.out.println("Iniciando programa: PERFIL DE PRODUCCIÓN");
    }

    @Override
    public void mostrarDespedida() {
        System.out.println("Cerrando programa: PERFIL DE PRODUCCIÓN");
    }
}
