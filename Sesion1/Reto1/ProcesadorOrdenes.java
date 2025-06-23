import java.util.List;

public class ProcesadorOrdenes {

    // Muestra cualquier tipo de orden
    public static void mostrarOrdenes(List<? extends OrdenProduccion> lista) {
        for (OrdenProduccion orden : lista) {
            orden.mostrarResumen();
            System.out.println("---");
        }
    }

    // Procesa solo órdenes personalizadas
    public static void procesarPersonalizadas(List<? super OrdenPersonalizada> lista, int costoAdicional) {
        for (Object obj : lista) {
            if (obj instanceof OrdenPersonalizada) {
                OrdenPersonalizada op = (OrdenPersonalizada) obj;
                op.agregarCosto(costoAdicional);
            }
        }
    }
}
