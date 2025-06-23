public class OrdenPersonalizada extends OrdenProduccion {
    private String cliente;

    public OrdenPersonalizada(String codigo, int cantidad, String cliente) {
        super(codigo, cantidad);
        this.cliente = cliente;
    }

    @Override
    public void mostrarResumen() {
        super.mostrarResumen();
        System.out.println("Cliente: " + cliente);
    }

    public void agregarCosto(int costo) {
        System.out.println("Se agregó un costo adicional de $" + costo + " para el cliente " + cliente);
    }
}
