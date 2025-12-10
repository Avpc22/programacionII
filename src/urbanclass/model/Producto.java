package urbanclass.model;

public class Producto {
    private final int id;
    private String nombre;
    private String categoria;
    private String talla;
    private String color;
    private double precio;
    private int cantidad;

    public Producto(int id, String nombre, String categoria, String talla,
                    String color, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.talla = talla;
        this.color = color;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void agregarStock(int cantidad) {
        this.cantidad += cantidad;
    }

    public boolean vender(int cantidad) {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-12s | %-6s | %-10s | $%-8.2f | %-8d |",
                id, nombre, categoria, talla, color, precio, cantidad);
    }
}

