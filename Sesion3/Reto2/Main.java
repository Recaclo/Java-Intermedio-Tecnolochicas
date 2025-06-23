package Sesion3.Reto2;

import java.util.*;
import java.util.stream.*;

class Encuesta {
    private String paciente;
    private String comentario; // puede ser null
    private int calificacion;

    public Encuesta(String paciente, String comentario, int calificacion) {
        this.paciente = paciente;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public String getPaciente() {
        return paciente;
    }

    public Optional<String> getComentario() {
        return Optional.ofNullable(comentario);
    }

    public int getCalificacion() {
        return calificacion;
    }
}

class Sucursal {
    private String nombre;
    private List<Encuesta> encuestas;

    public Sucursal(String nombre, List<Encuesta> encuestas) {
        this.nombre = nombre;
        this.encuestas = encuestas;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Encuesta> getEncuestas() {
        return encuestas;
    }
}

public class Main {
    public static void main(String[] args) {
        // Datos ejemplo
        List<Sucursal> sucursales = List.of(
            new Sucursal("Centro", List.of(
                new Encuesta("Ana", "El tiempo de espera fue largo", 2),
                new Encuesta("Luis", null, 1),
                new Encuesta("Marta", "Muy buen trato", 5)
            )),
            new Sucursal("Norte", List.of(
                new Encuesta("Carlos", "La atención fue buena, pero la limpieza puede mejorar", 3),
                new Encuesta("Sofía", null, 4)
            ))
        );

        // Procesamiento con Stream API y flatMap
        sucursales.stream()
            // Para cada sucursal, obtenemos un stream de encuestas
            .flatMap(sucursal -> 
                sucursal.getEncuestas().stream()
                    // Filtramos encuestas con calificación <= 3
                    .filter(encuesta -> encuesta.getCalificacion() <= 3)
                    // Para cada encuesta, obtenemos el Optional comentario
                    .flatMap(encuesta -> 
                        encuesta.getComentario()
                            .map(comentario -> 
                                // Transformamos comentario en mensaje con nombre sucursal
                                "Sucursal " + sucursal.getNombre() + 
                                ": Seguimiento a paciente con comentario: \"" + comentario + "\""
                            )
                            // Si no hay comentario, stream vacío (lo ignoramos)
                            .stream()
                    )
            )
            // Imprimimos los mensajes resultantes
            .forEach(System.out::println);
    }
}
