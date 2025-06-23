package Sesion2.Reto1;
import java.util.concurrent.*;

// Parte 1️⃣: Clases que simulan subsistemas
class SistemaNavegacion implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1000); // Simulación de procesamiento
        return " Navegación: trayectoria corregida con éxito.";
    }
}

class SistemaSoporteVital implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(800);
        return " Soporte vital: presión y oxígeno dentro de parámetros normales.";
    }
}

class SistemaControlTermico implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1200);
        return " Control térmico: temperatura estable (22°C).";
    }
}

class SistemaComunicaciones implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(700);
        return " Comunicaciones: enlace con estación terrestre establecido.";
    }
}

public class MisionEspacial {
    public static void main(String[] args) throws Exception {
        System.out.println(" Simulación de misión espacial iniciada...");

        // Parte 2️⃣: Ejecutar tareas con ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<String> futuroNavegacion = executor.submit(new SistemaNavegacion());
        Future<String> futuroSoporteVital = executor.submit(new SistemaSoporteVital());
        Future<String> futuroControlTermico = executor.submit(new SistemaControlTermico());
        Future<String> futuroComunicaciones = executor.submit(new SistemaComunicaciones());

        // Parte 3️⃣: Mostrar los resultados
        System.out.println(futuroComunicaciones.get());
        System.out.println(futuroSoporteVital.get());
        System.out.println(futuroControlTermico.get());
        System.out.println(futuroNavegacion.get());

        executor.shutdown(); // Cierre del servicio
        System.out.println(" Todos los sistemas reportan estado operativo.");
    }
}
