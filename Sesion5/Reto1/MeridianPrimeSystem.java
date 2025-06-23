package Sesion5.Reto1;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class MeridianPrimeSystem {

    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        // 🚗 Tráfico
        Flux<String> trafico = Flux.interval(Duration.ofMillis(500))
                .map(i -> random.nextInt(101))
                .filter(nivel -> nivel > 70)
                .map(nivel -> "🚗 Alerta: Congestión del " + nivel + "% en Avenida Solar")
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.parallel());

        // 🌫️ Contaminación
        Flux<String> contaminacion = Flux.interval(Duration.ofMillis(600))
                .map(i -> random.nextInt(100))
                .filter(pm -> pm > 50)
                .map(pm -> "🌫️ Alerta: Contaminación alta (PM2.5: " + pm + " ug/m3)")
                .subscribeOn(Schedulers.parallel());

        // 🚑 Accidentes
        String[] prioridades = {"Baja", "Media", "Alta"};
        Flux<String> accidentes = Flux.interval(Duration.ofMillis(800))
                .map(i -> prioridades[random.nextInt(3)])
                .filter(p -> p.equals("Alta"))
                .map(p -> "🚑 Emergencia vial: Accidente con prioridad " + p)
                .subscribeOn(Schedulers.parallel());

        // 🚝 Trenes maglev
        Flux<String> trenes = Flux.interval(Duration.ofMillis(700))
                .map(i -> random.nextInt(11))
                .filter(delay -> delay > 5)
                .map(delay -> "🚝 Tren maglev con retraso crítico: " + delay + " minutos")
                .delayElements(Duration.ofMillis(200)) // Simula backpressure
                .subscribeOn(Schedulers.parallel());

        // 🚦 Semáforos
        String[] estados = {"Verde", "Amarillo", "Rojo"};
        Flux<String> semaforos = Flux.interval(Duration.ofMillis(400))
                .map(i -> estados[random.nextInt(3)])
                .buffer(3, 1)
                .filter(lista -> lista.equals(List.of("Rojo", "Rojo", "Rojo")))
                .map(lista -> "🚦 Semáforo en Rojo detectado 3 veces seguidas en cruce Norte")
                .subscribeOn(Schedulers.parallel());

        // 🌐 Unir flujos
        Flux<String> sistemaGlobal = Flux.merge(trafico, contaminacion, accidentes, trenes, semaforos)
                .publish()
                .autoConnect();

        // 🔔 Mostrar eventos críticos
        sistemaGlobal
                .doOnNext(System.out::println)
                .buffer(Duration.ofSeconds(1)) // Agrupar eventos por segundo
                .filter(lista -> lista.size() >= 3)
                .map(lista -> "🚨 Alerta global: Múltiples eventos críticos detectados en Meridian Prime")
                .subscribe(System.out::println);

        // 💤 Mantener aplicación corriendo
        Thread.sleep(20000); // Simula ejecución por 20 segundos
    }
}
