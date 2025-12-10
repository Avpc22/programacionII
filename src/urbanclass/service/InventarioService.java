package urbanclass.service;

import urbanclass.model.Producto;
import urbanclass.util.ArchivoProductos;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventarioService {
    private List<Producto> productos;
    private int siguienteId;

    public InventarioService() {
        this.productos = new ArrayList<>();
        this.siguienteId = 1;
        cargarProductos();
    }

    private void cargarProductos() {
        productos = ArchivoProductos.cargarProductos();
        if (productos.isEmpty()) {
            cargarDatosIniciales();
            guardarProductos();
        } else {
            siguienteId = ArchivoProductos.obtenerMaxId(productos) + 1;
        }
    }

    private void guardarProductos() {
        ArchivoProductos.guardarProductos(productos);
    }

    private void cargarDatosIniciales() {
        agregarProductoSinGuardar("Camiseta Basica", "Camisetas", "M", "Blanco", 19.99, 50);
        agregarProductoSinGuardar("Jeans Slim", "Pantalones", "32", "Azul", 49.99, 30);
        agregarProductoSinGuardar("Hoodie Urban", "Sudaderas", "L", "Negro", 39.99, 25);
        agregarProductoSinGuardar("Vestido Casual", "Vestidos", "S", "Rojo", 59.99, 15);
        agregarProductoSinGuardar("Chaqueta Denim", "Chaquetas", "M", "Azul", 79.99, 20);
    }

    private void agregarProductoSinGuardar(String nombre, String categoria, String talla,
                                String color, double precio, int cantidad) {
        Producto producto = new Producto(siguienteId++, nombre, categoria, talla,
                color, precio, cantidad);
        productos.add(producto);
    }

    public void agregarProducto(String nombre, String categoria, String talla,
                                String color, double precio, int cantidad) {
        Producto producto = new Producto(siguienteId++, nombre, categoria, talla,
                color, precio, cantidad);
        productos.add(producto);
        guardarProductos();
    }

    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productos.stream()
                .filter(p -> p.getCategoria().toLowerCase().contains(categoria.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean eliminarProducto(int id) {
        boolean eliminado = productos.removeIf(p -> p.getId() == id);
        if (eliminado) {
            guardarProductos();
        }
        return eliminado;
    }

    public boolean actualizarStock(int id, int cantidad) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            int nuevoStock = producto.getCantidad() + cantidad;
            if (nuevoStock >= 0) {
                producto.agregarStock(cantidad);
                guardarProductos();
                return true;
            }
        }
        return false;
    }

    public boolean realizarVenta(int id, int cantidad) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            return producto.vender(cantidad);
        }
        return false;
    }

    public List<Producto> obtenerStockBajo(int minimo) {
        return productos.stream()
                .filter(p -> p.getCantidad() <= minimo)
                .collect(Collectors.toList());
    }

    public double obtenerValorTotal() {
        return productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();
    }

    public int obtenerTotalProductos() {
        return productos.size();
    }

    public int obtenerTotalUnidades() {
        return productos.stream()
                .mapToInt(Producto::getCantidad)
                .sum();
    }

    public void imprimirEncabezado() {
        System.out.println("+------+----------------------+--------------+--------+------------+----------+----------+");
        System.out.println("| ID   | Nombre               | Categoria    | Talla  | Color      | Precio   | Stock    |");
        System.out.println("+------+----------------------+--------------+--------+------------+----------+----------+");
    }

    public void imprimirPiePagina() {
        System.out.println("+------+----------------------+--------------+--------+------------+----------+----------+");
    }
}
