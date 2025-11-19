package com.utn.tareas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {
    Long id;
    String descripcion;
    boolean completada;
    Prioridad prioridad;
}
