package Sesion4.Reto1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MovilidadApp {

    // Simula calcular la ruta óptima con latencia 2-3 segundos
    public CompletableFuture<String> calcularRuta() {
        System.out.println(" Calculando ruta...");
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Latencia aleatoria entre 2 y 3 segundos
                int delay = 2000 + ThreadLocalRandom.current().nextInt(1000);
                TimeUnit.MILLISECONDS.sleep(delay);

                // Aquí se podría simular una excepción, p.ej. con probabilidad baja
                if (ThreadLocalRandom.current().nextInt(10) == 0) {
                    throw new RuntimeException("Error al calcular la ruta");
                }

                return "Centro -> Norte";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Simula estimar la tarifa con latencia 1-2 segundos
    public CompletableFuture<Double> estimarTarifa() {
        System.out.println(" Estimando tarifa...");
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Latencia aleatoria entre 1 y 2 segundos
                int delay = 1000 + ThreadLocalRandom.current().nextInt(1000);
                TimeUnit.MILLISECONDS.sleep(delay);

                // Simular excepción con baja probabilidad
                if (ThreadLocalRandom.current().nextInt(10) == 1) {
                    throw new RuntimeException("Error al estimar la tarifa");
                }

                return 75.50;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Método que combina ambas operaciones y maneja errores
    public void procesarViaje() {
        calcularRuta()
            .thenCombine(estimarTarifa(), (ruta, tarifa) -> 
                String.format(" Ruta calculada: %s | Tarifa estimada: $%.2f", ruta, tarifa)
            )
            .exceptionally(ex -> {
                // Si alguna operación falla
                return " Ocurrió un error: " + ex.getMessage();
            })
            .thenAccept(System.out::println);
    }

    // Main para probar
    public static void main(String[] args) throws InterruptedException {
        MovilidadApp app = new MovilidadApp();
        app.procesarViaje();

        // Para que el programa no termine antes de que se completen las tareas
        Thread.sleep(5000);
    }
}
