package Sesion4.Reto2;

import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

public class AeropuertoControl {

    // Probabilidades de éxito de cada servicio (0 a 1)
    private static final double PROB_PISTA = 0.8;
    private static final double PROB_CLIMA = 0.85;
    private static final double PROB_TRAFICO = 0.9;
    private static final double PROB_PERSONAL = 0.95;

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public CompletableFuture<Boolean> verificarPista() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean resultado = lanzarConProbabilidad(PROB_PISTA);
            System.out.println(" Pista disponible: " + resultado);
            return resultado;
        }, executor).exceptionally(ex -> {
            System.out.println("Error verificando pista: " + ex.getMessage());
            return false;
        });
    }

    public CompletableFuture<Boolean> verificarClima() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean resultado = lanzarConProbabilidad(PROB_CLIMA);
            System.out.println(" Clima favorable: " + resultado);
            return resultado;
        }, executor).exceptionally(ex -> {
            System.out.println("Error verificando clima: " + ex.getMessage());
            return false;
        });
    }

    public CompletableFuture<Boolean> verificarTraficoAereo() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean resultado = lanzarConProbabilidad(PROB_TRAFICO);
            System.out.println(" Tráfico aéreo despejado: " + resultado);
            return resultado;
        }, executor).exceptionally(ex -> {
            System.out.println("Error verificando tráfico aéreo: " + ex.getMessage());
            return false;
        });
    }

    public CompletableFuture<Boolean> verificarPersonalTierra() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean resultado = lanzarConProbabilidad(PROB_PERSONAL);
            System.out.println(" Personal disponible: " + resultado);
            return resultado;
        }, executor).exceptionally(ex -> {
            System.out.println("Error verificando personal en tierra: " + ex.getMessage());
            return false;
        });
    }

    private void simularLatencia() {
        try {
            // Latencia entre 2000 y 3000 ms
            Thread.sleep(ThreadLocalRandom.current().nextInt(2000, 3001));
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupción durante latencia");
        }
    }

    private boolean lanzarConProbabilidad(double probabilidad) {
        return ThreadLocalRandom.current().nextDouble() < probabilidad;
    }

    public void verificarAterrizaje() {
        System.out.println(" Verificando condiciones para aterrizaje...\n");

        CompletableFuture<Boolean> pista = verificarPista();
        CompletableFuture<Boolean> clima = verificarClima();
        CompletableFuture<Boolean> trafico = verificarTraficoAereo();
        CompletableFuture<Boolean> personal = verificarPersonalTierra();

        CompletableFuture<Void> all = CompletableFuture.allOf(pista, clima, trafico, personal);

        all.thenRun(() -> {
            try {
                boolean ok = pista.get() && clima.get() && trafico.get() && personal.get();
                if (ok) {
                    System.out.println("\n Aterrizaje autorizado: todas las condiciones óptimas.");
                } else {
                    System.out.println("\n Aterrizaje denegado: condiciones no óptimas.");
                }
            } catch (Exception e) {
                System.out.println("\n Aterrizaje denegado: error en la verificación.");
            } finally {
                executor.shutdown();
            }
        }).exceptionally(ex -> {
            System.out.println("\n Aterrizaje denegado: error inesperado - " + ex.getMessage());
            executor.shutdown();
            return null;
        });
    }

    public static void main(String[] args) {
        AeropuertoControl control = new AeropuertoControl();
        control.verificarAterrizaje();

        // Para evitar que el programa termine antes de las tareas asíncronas:
        try {
            Thread.sleep(5000); // Espera suficiente para que terminen las verificaciones
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
