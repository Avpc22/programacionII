package urbanclass.util;

import urbanclass.model.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoProductos {
    private static final String ARCHIVO = "datos/productos.txt";

    public static void guardarProductos(List<Producto> productos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Producto p : productos) {
                writer.println(p.getId() + "|" + p.getNombre() + "|" + p.getCategoria() + "|" +
                        p.getTalla() + "|" + p.getColor() + "|" + p.getPrecio() + "|" + p.getCantidad());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    public static List<Producto> cargarProductos() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 7) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String categoria = datos[2];
                    String talla = datos[3];
                    String color = datos[4];
                    double precio = Double.parseDouble(datos[5]);
                    int cantidad = Integer.parseInt(datos[6]);
                    productos.add(new Producto(id, nombre, categoria, talla, color, precio, cantidad));
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe, es normal en primera ejecución
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
        return productos;
    }

    public static int obtenerMaxId(List<Producto> productos) {
        return productos.stream().mapToInt(Producto::getId).max().orElse(0);
    }
}
