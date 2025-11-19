package com.utn.tareas.service;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TareaService {
    final TareaRepository tareaRepository;

    @Value("${app.nombre}")
    private String appNombre;

    @Value("${app.max-tareas}")
    private int maxTareas;

    @Value("${app.mostrar-estadisticas}")
    private boolean mostrarEstadisticas;


    public Tarea agregarTarea(String descripcion, Prioridad prioridad) {

        if(this.tareaRepository.obtenerTareas().size() >= maxTareas){
            throw new IllegalStateException("Máximo de tareas alcanzado ("+maxTareas+")");
        }

        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setDescripcion(descripcion);
        nuevaTarea.setPrioridad(prioridad);
        nuevaTarea.setCompletada(false);
        return tareaRepository.guardarTarea(nuevaTarea);
    }

    public List<Tarea> listarTodasLasTareas() {
        return tareaRepository.obtenerTareas();
    }
    public List<Tarea> listarTareasPendientes() {
        return tareaRepository.obtenerTareas().stream()
                .filter(tarea -> !tarea.isCompletada())
                .collect(Collectors.toList());
    }
    public List<Tarea> listarTareasCompletadas() {
        return tareaRepository.obtenerTareas().stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
    }
    public boolean marcarTareaComoCompletada(Long id) {
        Optional<Tarea> tareaOptional = tareaRepository.buscarPorId(id);

        if (tareaOptional.isPresent()) {
            Tarea tarea = tareaOptional.get();
            tarea.setCompletada(true);
            tareaRepository.guardarTarea(tarea);
            return true;
        }
        return false;
    }
    public String obtenerEstadisticas() {
        List<Tarea> todas = listarTodasLasTareas();
        long total = todas.size();
        long completadas = listarTareasCompletadas().size();
        long pendientes = listarTareasPendientes().size();

        return String.format(
                "Estadísticas de Tareas:\n" +
                        "  Total: %d\n" +
                        "  Completadas: %d\n" +
                        "  Pendientes: %d",
                total, completadas, pendientes
        );
    }

    public String imprimirConfiguracion() {
        return String.format(
                "--- Configuración Actual ---\n" +
                        "  Nombre de App: %s\n" +
                        "  Límite Máximo de Tareas: %d\n" +
                        "  Mostrar Estadísticas: %b\n" +
                        "----------------------------",
                appNombre, maxTareas, mostrarEstadisticas
        );
    }
}
