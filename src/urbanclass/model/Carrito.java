package urbanclass.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private Cliente cliente;
    private List<ItemCarrito> items;

    public Carrito(Cliente cliente) {
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemCarrito> getItems() {
        return new ArrayList<>(items);
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void removerProducto(int idProducto) {
        items.removeIf(item -> item.getProducto().getId() == idProducto);
    }

    public double getTotal() {
        return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }

    public int getTotalItems() {
        return items.stream().mapToInt(ItemCarrito::getCantidad).sum();
    }

    public void limpiar() {
        items.clear();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Carrito vacío";
        }
        StringBuilder sb = new StringBuilder();
        for (ItemCarrito item : items) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Total: $").append(String.format("%.2f", getTotal()));
        return sb.toString();
    }
}
