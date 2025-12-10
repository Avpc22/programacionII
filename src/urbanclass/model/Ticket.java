package urbanclass.model;

import urbanclass.util.StringUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ticket {
    private Cliente cliente;
    private List<ItemCarrito> items;
    private double total;
    private LocalDateTime fecha;
    private Pago pago;

    public Ticket(Cliente cliente, List<ItemCarrito> items, double total) {
        this.cliente = cliente;
        this.items = items;
        this.total = total;
        this.fecha = LocalDateTime.now();
        this.pago = null;
    }

    public Ticket(Cliente cliente, List<ItemCarrito> items, double total, Pago pago) {
        this.cliente = cliente;
        this.items = items;
        this.total = total;
        this.fecha = LocalDateTime.now();
        this.pago = pago;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public void imprimir() {
        String linea = StringUtil.repeat("=", 50);
        String guion = StringUtil.repeat("-", 50);
        
        System.out.println("\n" + linea);
        System.out.println("                   TICKET DE VENTA");
        System.out.println(linea);
        System.out.println("\nFecha: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("\n--- DATOS DEL CLIENTE ---");
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Telefono: " + cliente.getTelefono());
        System.out.println("\n--- PRODUCTOS COMPRADOS ---");
        System.out.println(String.format("%-25s %10s %10s %12s", "Producto", "Cantidad", "Precio", "Subtotal"));
        System.out.println(guion);
        
        for (ItemCarrito item : items) {
            Producto p = item.getProducto();
            System.out.println(String.format("%-25s %10d $%9.2f $%11.2f",
                    p.getNombre(), item.getCantidad(), p.getPrecio(), item.getSubtotal()));
        }
        
        System.out.println(guion);
        System.out.println(String.format("%46s $%11.2f", "TOTAL:", total));
        
        if (pago != null) {
            System.out.println("\n--- METODO DE PAGO ---");
            System.out.println("Metodo: " + pago.getMetodoPago());
            System.out.println("Monto pagado: $" + String.format("%.2f", pago.getMonto()));
            
            if (pago.getMetodoPago().equalsIgnoreCase("Efectivo") && pago.getVuelto() > 0) {
                System.out.println("Vuelto: $" + String.format("%.2f", pago.getVuelto()));
            } else if (pago.getMetodoPago().equalsIgnoreCase("Transferencia") && pago.getBanco() != null) {
                System.out.println("Banco: " + pago.getBanco());
                System.out.println("Cuenta: " + pago.getNumeroCuenta());
            }
        }
        
        System.out.println(linea);
        System.out.println("Gracias por su compra!");
        System.out.println(linea + "\n");
    }

    public void guardarEnArchivo() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("datos/tickets.txt", true))) {
            String linea = StringUtil.repeat("=", 50);
            String guion = StringUtil.repeat("-", 50);
            
            writer.println("\n" + linea);
            writer.println("TICKET - " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println("Cliente: " + cliente.getNombre() + " (" + cliente.getEmail() + ")");
            writer.println("Telefono: " + cliente.getTelefono());
            writer.println(guion);
            for (ItemCarrito item : items) {
                Producto p = item.getProducto();
                writer.println(String.format("%-25s %10d x $%.2f = $%.2f",
                        p.getNombre(), item.getCantidad(), p.getPrecio(), item.getSubtotal()));
            }
            writer.println(guion);
            writer.println(String.format("TOTAL: $%.2f", total));
            
            if (pago != null) {
                writer.println("Metodo de pago: " + pago.getMetodoPago());
                writer.println("Monto pagado: $" + String.format("%.2f", pago.getMonto()));
            }
            
            writer.println(linea);
        } catch (java.io.IOException e) {
            System.out.println("Error al guardar ticket: " + e.getMessage());
        }
    }
}
