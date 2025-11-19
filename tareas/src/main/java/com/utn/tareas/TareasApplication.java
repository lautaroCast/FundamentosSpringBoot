package com.utn.tareas;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.service.MensajeService;
import com.utn.tareas.service.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class TareasApplication implements CommandLineRunner {
    public static Scanner sc = new Scanner(System.in);
    private final TareaService tareaService;
    private final MensajeService mensajeService;

    public TareasApplication(TareaService tareaService, MensajeService mensajeService) {
        this.tareaService = tareaService;
        this.mensajeService = mensajeService;
    }


    public static void main(String[] args) {
		SpringApplication.run(TareasApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        mensajeService.mostrarBienvenida();
        //-------------------------------------------------------------------------------------------------------
        System.out.println(tareaService.imprimirConfiguracion());
        //-------------------------------------------------------------------------------------------------------
        System.out.println("TAREAS INICIALES (" + tareaService.listarTodasLasTareas().size() + ")");
        System.out.println("----------------------------------------");
        tareaService.listarTodasLasTareas().forEach(tarea -> {
            String estado = tarea.isCompletada() ? "Completada" : "Pendiente";

            System.out.printf("[%d] (%s) %s - %s\n",
                    tarea.getId(),
                    tarea.getPrioridad(),
                    estado,
                    tarea.getDescripcion()
            );
        });
        //-------------------------------------------------------------------------------------------------------
        System.out.println("\nAGREGANDO NUEVA TAREA...");
        Tarea nueva = tareaService.agregarTarea("Investigar Profiles de Spring", Prioridad.ALTA);
        System.out.printf("Tarea agregada. ID asignado: %d. Total de tareas: %d\n",
                nueva.getId(), tareaService.listarTodasLasTareas().size());
        //-------------------------------------------------------------------------------------------------------
        System.out.println("TAREAS PENDIENTES (" + tareaService.listarTareasPendientes().size() + ")");
        System.out.println("----------------------------------------");
        tareaService.listarTareasPendientes().forEach(tarea -> {

            System.out.printf("[%d] (%s) - %s\n",
                    tarea.getId(),
                    tarea.getPrioridad(),
                    tarea.getDescripcion()
            );
        });
        //-------------------------------------------------------------------------------------------------------
        final Long ID_A_COMPLETAR = nueva.getId();
        System.out.println("\nMARCANDO TAREA ID " + ID_A_COMPLETAR + " COMO COMPLETADA...");

        boolean completada = tareaService.marcarTareaComoCompletada(ID_A_COMPLETAR);
        //-------------------------------------------------------------------------------------------------------
        if (completada) {
            System.out.println("----------------------------------------");
            System.out.printf("Tarea %d ha sido marcada como completada con éxito.\n", ID_A_COMPLETAR);
        } else {
            System.out.println("----------------------------------------");
            System.out.printf("Error: No se pudo marcar la Tarea %d.\n", ID_A_COMPLETAR);
        }
        //-------------------------------------------------------------------------------------------------------
        System.out.println("----------------------------------------");
        System.out.println(tareaService.obtenerEstadisticas());
        //-------------------------------------------------------------------------------------------------------
        List<Tarea> completadas = tareaService.listarTareasCompletadas();

        System.out.println("\nTAREAS COMPLETADAS (" + completadas.size() + ")");
        System.out.println("----------------------------------------");

        completadas.forEach(tarea -> {
            String estado = "COMPLETADA";
            System.out.printf("[%d] (%s) %s - %s\n",
                    tarea.getId(),
                    tarea.getPrioridad(),
                    estado,
                    tarea.getDescripcion()
            );
        });
        System.out.println("----------------------------------------");
        //-------------------------------------------------------------------------------------------------------
        mensajeService.mostrarDespedida();
    }
}
