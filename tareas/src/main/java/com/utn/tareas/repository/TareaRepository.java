package com.utn.tareas.repository;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.utn.tareas.TareasApplication.sc;


@Repository
public class TareaRepository {
    List<Tarea> tareas;
    private final AtomicLong nextId = new AtomicLong(1);


    public TareaRepository(){
        this.tareas = new ArrayList<>(List.of(
                new Tarea(1L, "Configurar proyecto Spring Boot", true, Prioridad.ALTA),
                new Tarea(2L, "Crear clase Tarea", true, Prioridad.MEDIA),
                new Tarea(3L, "Crear clase TareaRepository", true, Prioridad.BAJA)
        ));
        this.nextId.set(this.tareas.size() + 1);
    }

    public Tarea guardarTarea(Tarea tarea){
        if (tarea.getId() == null) {
            // 1. Inserción: Asignar nuevo ID y añadir a la lista
            tarea.setId(nextId.getAndIncrement());
            tareas.add(tarea);
        } else {
            // 2. Actualización: Encontrar y reemplazar la tarea existente
            int index = -1;
            for (int i = 0; i < tareas.size(); i++) {
                if (tareas.get(i).getId().equals(tarea.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                // Reemplaza el objeto en la lista con la versión actualizada
                tareas.set(index, tarea);
            }
            // Nota: Podrías lanzar una excepción si el ID existe pero no se encuentra en la lista.
        }
        return tarea;
    }

    public List<Tarea> obtenerTareas(){
        return tareas;
    }

    public Optional<Tarea> buscarPorId(Long id){
        System.out.println("-Buscar tarea por ID-\n Ingrese el ID:");
        Long idIngresado = sc.nextLong();

        Optional<Tarea> resultado = tareas.stream()
                .filter(t -> t.getId() == idIngresado)
                .findFirst();

        System.out.println("Tarea encontrada:\n" + resultado);
        return resultado;
    }

    public void eliminarTarea(){
        System.out.println("-Eliminar tarea por ID-\n Ingrese el ID de la tarea a eliminar:");
        Long idIngresado = sc.nextLong();

        Optional<Tarea> tareaPorEliminar = tareas.stream()
                .filter(t -> t.getId().equals(idIngresado))
                .findFirst();

        tareaPorEliminar.ifPresentOrElse(
                tarea -> {
                    tareas.remove(tarea);
                    System.out.println("Tarea con ID " + idIngresado + " eliminada exitosamente.");
                },
                () -> System.out.println("Error: No se encontró ninguna tarea con el ID " + idIngresado + ".")
        );
    }
}
