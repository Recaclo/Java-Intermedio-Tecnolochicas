package Sesion2.Reto2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

class RecursoMedico {
    private final String nombre;
    private final ReentrantLock lock = new ReentrantLock();

    public RecursoMedico(String nombre) {
        this.nombre = nombre;
    }

    public void usar(String profesional) {
        System.out.println(" " + profesional + " intentando acceder a " + nombre + "...");
        lock.lock();  // Adquiere el candado
        try {
            System.out.println(" " + profesional + " ha ingresado a " + nombre);
            Thread.sleep(2000); // Simula el uso del recurso
            System.out.println("" + profesional + " ha salido de " + nombre);
        } catch (InterruptedException e) {
            System.out.println(" " + profesional + " fue interrumpido.");
            Thread.currentThread().interrupt(); // Restablece el estado del hilo
        } finally {
            lock.unlock();  // Libera el candado
        }
    }
}

public class SimulacionHospital {
    public static void main(String[] args) {
        RecursoMedico salaCirugia = new RecursoMedico("Sala de cirugía");

        Runnable draSanchez = () -> salaCirugia.usar("Dra. Sánchez");
        Runnable drGomez = () -> salaCirugia.usar("Dr. Gómez");
        Runnable enfermeroLopez = () -> salaCirugia.usar("Enfermero López");
        Runnable draMartinez = () -> salaCirugia.usar("Dra. Martínez");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        System.out.println(" Iniciando acceso a la Sala de cirugía...\n");

        executor.execute(draSanchez);
        executor.execute(drGomez);
        executor.execute(enfermeroLopez);
        executor.execute(draMartinez);

        executor.shutdown(); // Finaliza el executor después de las tareas
    }
}
