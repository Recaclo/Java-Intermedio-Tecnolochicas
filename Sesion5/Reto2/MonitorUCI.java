package Sesion5.Reto2;

package Sesion6.RetoUCI;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;

public class MonitorUCI {

    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Flux<String> paciente1 = generarFlujoPaciente(1);
        Flux<String> paciente2 = generarFlujoPaciente(2);
        Flux<String> paciente3 = generarFlujoPaciente(3);

        Flux.merge(paciente1, paciente2, paciente3)
            .filter(alerta -> !alerta.isEmpty()) // solo eventos críticos
            .delayElements(Duration.ofSeconds(1)) // backpressure
            .subscribe(System.out::println);

        // Mantener la app viva por 30 segundos
        Thread.sleep(30000);
    }

    private static Flux<String> generarFlujoPaciente(int idPaciente) {
        return Flux.interval(Duration.ofMillis(300))
                .map(i -> generarSignosVitales(idPaciente))
                .subscribeOn(Schedulers.parallel());
    }

    private static String generarSignosVitales(int pacienteId) {
        int fc = 40 + random.nextInt(100); // 40–139 bpm
        int paSistolica = 80 + random.nextInt(80); // 80–159 mmHg
        int paDiastolica = 50 + random.nextInt(50); // 50–99 mmHg
        int spo2 = 85 + random.nextInt(15); // 85–99 %

        // Priorizar FC sobre otros
        if (fc < 50 || fc > 120) {
            return " Paciente " + pacienteId + " - FC crítica: " + fc + " bpm";
        }
        if (paSistolica < 90 || paDiastolica < 60 || paSistolica > 140 || paDiastolica > 90) {
            return " Paciente " + pacienteId + " - PA crítica: " + paSistolica + "/" + paDiastolica + " mmHg";
        }
        if (spo2 < 90) {
            return " Paciente " + pacienteId + " - SpO₂ baja: " + spo2 + "%";
        }

        return ""; // no crítico
    }
}
