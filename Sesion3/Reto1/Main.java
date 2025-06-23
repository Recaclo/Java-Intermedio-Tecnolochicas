package Sesion3.Reto1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<Pedido> pedidos = Arrays.asList(
            new Pedido("Juan", "domicilio", "555-1234"),
            new Pedido("Ana", "local", "555-0000"),
            new Pedido("Luis", "domicilio", null),
            new Pedido("Marta", "domicilio", "555-5678")
        );

        pedidos.stream()
            .filter(p -> "domicilio".equalsIgnoreCase(p.getTipoEntrega()))
            .map(Pedido::getTelefono)
            .flatMap(Optional::stream)
            .map(tel -> " Confirmación enviada al número: " + tel)
            .forEach(System.out::println);
    }
}
