import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear órdenes
        List<OrdenMasa> ordenesMasa = new ArrayList<>();
        ordenesMasa.add(new OrdenMasa("M001", 100));
        ordenesMasa.add(new OrdenMasa("M002", 200));

        List<OrdenPersonalizada> ordenesPersonalizadas = new ArrayList<>();
        ordenesPersonalizadas.add(new OrdenPersonalizada("P001", 50, "Cliente A"));
        ordenesPersonalizadas.add(new OrdenPersonalizada("P002", 30, "Cliente B"));

        List<OrdenPrototipo> ordenesPrototipo = new ArrayList<>();
        ordenesPrototipo.add(new OrdenPrototipo("T001", 5, "Fase 1"));
        ordenesPrototipo.add(new OrdenPrototipo("T002", 3, "Fase 2"));

        System.out.println("=== Mostrando todas las órdenes ===");
        ProcesadorOrdenes.mostrarOrdenes(ordenesMasa);
        ProcesadorOrdenes.mostrarOrdenes(ordenesPersonalizadas);
        ProcesadorOrdenes.mostrarOrdenes(ordenesPrototipo);

        System.out.println("\n=== Procesando órdenes personalizadas ===");
        ProcesadorOrdenes.procesarPersonalizadas(ordenesPersonalizadas, 150);
    }
}
