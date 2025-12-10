package urbanclass.ui;

import urbanclass.model.Producto;
import urbanclass.model.Cliente;
import urbanclass.model.Carrito;
import urbanclass.model.ItemCarrito;
import urbanclass.model.Ticket;
import urbanclass.model.Pago;
import urbanclass.service.InventarioService;
import urbanclass.util.InputReader;
import urbanclass.util.TicketHelper;
import urbanclass.util.PagoValidator;
import java.util.List;

public class InventarioUI {
    private InventarioService inventarioService;
    private InputReader inputReader;

    public InventarioUI() {
        this.inventarioService = new InventarioService();
        this.inputReader = new InputReader();
    }

    public void iniciar() {
        System.out.println("\n========================================");
        System.out.println("   URBANCLASS - Sistema de Inventario   ");
        System.out.println("========================================\n");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = inputReader.leerEntero("Seleccione una opcion: ");
            
            switch (opcion) {
                case 1:
                    listarProductos();
                    break;
                case 2:
                    gestionProductos();
                    break;
                case 3:
                    realizarVentaConCarrito();
                    break;
                case 4:
                    verHistorialTickets();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("\nGracias por usar UrbanClass. Hasta pronto!");
                    break;
                default:
                    System.out.println("\nOpcion no valida. Intente de nuevo.");
            }
        }
        inputReader.cerrar();
    }

    private void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Ver inventario");
        System.out.println("2. Gestion de productos");
        System.out.println("3. Realizar venta");
        System.out.println("4. Ver historial de tickets");
        System.out.println("0. Salir");
        System.out.println("----------------------");
    }

    private void listarProductos() {
        System.out.println("\n=== INVENTARIO COMPLETO ===\n");
        List<Producto> productos = inventarioService.obtenerTodos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }
        inventarioService.imprimirEncabezado();
        for (Producto p : productos) {
            System.out.println(p);
        }
        inventarioService.imprimirPiePagina();
    }

    private void gestionProductos() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenuGestion();
            int opcion = inputReader.leerEntero("Seleccione una opcion: ");
            
            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    buscarProducto();
                    break;
                case 3:
                    actualizarStock();
                    break;
                case 4:
                    eliminarProducto();
                    break;
                case 5:
                    verEstadisticas();
                    break;
                case 6:
                    verStockBajo();
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("\nOpcion no valida. Intente de nuevo.");
            }
        }
    }

    private void mostrarMenuGestion() {
        System.out.println("\n--- GESTION DE PRODUCTOS ---");
        System.out.println("1. Agregar nuevo producto");
        System.out.println("2. Buscar producto");
        System.out.println("3. Actualizar stock");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Ver estadisticas");
        System.out.println("6. Ver productos con stock bajo");
        System.out.println("0. Volver");
        System.out.println("----------------------------");
    }

    private void agregarProducto() {
        System.out.println("\n=== AGREGAR NUEVO PRODUCTO ===\n");
        
        String nombre = inputReader.leerTexto("Nombre del producto: ");
        String categoria = inputReader.leerTexto("Categoria: ");
        String talla = inputReader.leerTexto("Talla: ");
        String color = inputReader.leerTexto("Color: ");
        double precio = inputReader.leerDecimal("Precio: $");
        int cantidad = inputReader.leerEntero("Cantidad inicial: ");
        
        inventarioService.agregarProducto(nombre, categoria, talla, color, precio, cantidad);
        System.out.println("\n✓ Producto agregado exitosamente!");
    }

    private void buscarProducto() {
        System.out.println("\n=== BUSCAR PRODUCTO ===");
        System.out.println("1. Buscar por nombre");
        System.out.println("2. Buscar por categoria");
        System.out.println("3. Buscar por ID");
        
        int opcion = inputReader.leerEntero("Seleccione tipo de busqueda: ");
        List<Producto> resultados;
        
        switch (opcion) {
            case 1:
                String nombre = inputReader.leerTexto("Ingrese nombre a buscar: ");
                resultados = inventarioService.buscarPorNombre(nombre);
                mostrarResultados(resultados);
                break;
            case 2:
                String categoria = inputReader.leerTexto("Ingrese categoria a buscar: ");
                resultados = inventarioService.buscarPorCategoria(categoria);
                mostrarResultados(resultados);
                break;
            case 3:
                int id = inputReader.leerEntero("Ingrese ID del producto: ");
                Producto producto = inventarioService.buscarPorId(id);
                if (producto != null) {
                    inventarioService.imprimirEncabezado();
                    System.out.println(producto);
                    inventarioService.imprimirPiePagina();
                } else {
                    System.out.println("Producto no encontrado.");
                }
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    private void mostrarResultados(List<Producto> resultados) {
        if (resultados.isEmpty()) {
            System.out.println("\nNo se encontraron productos.");
            return;
        }
        System.out.println("\nResultados encontrados: " + resultados.size());
        inventarioService.imprimirEncabezado();
        for (Producto p : resultados) {
            System.out.println(p);
        }
        inventarioService.imprimirPiePagina();
    }

    private void actualizarStock() {
        System.out.println("\n=== ACTUALIZAR STOCK ===\n");
        listarProductos();
        
        int id = inputReader.leerEntero("\nID del producto a actualizar: ");
        int cantidad = inputReader.leerEntero("Cantidad a agregar (negativo para reducir): ");
        
        if (inventarioService.actualizarStock(id, cantidad)) {
            System.out.println("✓ Stock actualizado exitosamente!");
            Producto p = inventarioService.buscarPorId(id);
            System.out.println("Nuevo stock: " + p.getCantidad() + " unidades");
        } else {
            System.out.println("Error: Producto no encontrado o stock insuficiente.");
        }
    }

    private void eliminarProducto() {
        System.out.println("\n=== ELIMINAR PRODUCTO ===\n");
        listarProductos();
        
        int id = inputReader.leerEntero("\nID del producto a eliminar: ");
        
        Producto producto = inventarioService.buscarPorId(id);
        if (producto == null) {
            System.out.println("Error: Producto no encontrado.");
            return;
        }
        
        String confirmacion = inputReader.leerTexto("Esta seguro de eliminar '" + producto.getNombre() + "'? (si/no): ");
        if (confirmacion.equalsIgnoreCase("si")) {
            inventarioService.eliminarProducto(id);
            System.out.println("✓ Producto eliminado exitosamente!");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private void verEstadisticas() {
        System.out.println("\n=== ESTADISTICAS DEL INVENTARIO ===\n");
        System.out.println("Total de productos diferentes: " + inventarioService.obtenerTotalProductos());
        System.out.println("Total de unidades en stock: " + inventarioService.obtenerTotalUnidades());
        System.out.println("Valor total del inventario: $" + String.format("%.2f", inventarioService.obtenerValorTotal()));
    }

    private void verStockBajo() {
        System.out.println("\n=== PRODUCTOS CON STOCK BAJO ===\n");
        
        List<Producto> stockBajo = inventarioService.obtenerStockBajo(5);
        if (stockBajo.isEmpty()) {
            System.out.println("No hay productos con stock bajo.");
            return;
        }
        
        System.out.println("Productos con 5 unidades o menos:");
        inventarioService.imprimirEncabezado();
        for (Producto p : stockBajo) {
            System.out.println(p);
        }
        inventarioService.imprimirPiePagina();
    }

    private void realizarVentaConCarrito() {
        System.out.println("\n=== REALIZAR VENTA ===\n");
        
        // Solicitar datos del cliente
        System.out.println("--- DATOS DEL CLIENTE ---");
        String nombre = inputReader.leerTexto("Nombre completo: ");
        String email = inputReader.leerTexto("Email: ");
        String telefono = inputReader.leerTexto("Telefono: ");
        
        Cliente cliente = new Cliente(0, nombre, email, telefono);
        Carrito carrito = new Carrito(cliente);
        
        // Agregar productos al carrito
        boolean agregarMas = true;
        while (agregarMas) {
            listarProductos();
            
            int id = inputReader.leerEntero("\nID del producto (0 para terminar): ");
            if (id == 0) {
                agregarMas = false;
                break;
            }
            
            Producto producto = inventarioService.buscarPorId(id);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }
            
            int cantidad = inputReader.leerEntero("Cantidad a comprar: ");
            if (cantidad <= 0) {
                System.out.println("Cantidad invalida.");
                continue;
            }
            
            if (producto.getCantidad() < cantidad) {
                System.out.println("Stock insuficiente. Disponible: " + producto.getCantidad());
                continue;
            }
            
            carrito.agregarProducto(producto, cantidad);
            
            System.out.println("✓ " + producto.getNombre() + " agregado al carrito.");
            
            String continuar = inputReader.leerTexto("¿Desea agregar otro producto? (si/no): ");
            if (!continuar.equalsIgnoreCase("si")) {
                agregarMas = false;
            }
        }
        
        if (carrito.getTotalItems() == 0) {
            System.out.println("\nCarrito vacio. Venta cancelada.");
            return;
        }
        
        // Mostrar resumen del carrito
        System.out.println("\n--- RESUMEN DEL CARRITO ---");
        for (ItemCarrito item : carrito.getItems()) {
            Producto p = item.getProducto();
            System.out.println(String.format("%-25s %5d x $%8.2f = $%10.2f",
                    p.getNombre(), item.getCantidad(), p.getPrecio(), item.getSubtotal()));
        }
        System.out.println("-------------------------------------------");
        System.out.println(String.format("%31s TOTAL: $%10.2f", "", carrito.getTotal()));
        
        // Procesar pago
        Pago pago = procesarPago(carrito.getTotal());
        if (pago == null) {
            System.out.println("\nVenta cancelada.");
            return;
        }
        
        // Generar el ticket
        Ticket ticket = new Ticket(cliente, carrito.getItems(), carrito.getTotal(), pago);
        ticket.imprimir();
        ticket.guardarEnArchivo();
        
        // Descontar del inventario
        for (ItemCarrito item : carrito.getItems()) {
            inventarioService.realizarVenta(item.getProducto().getId(), item.getCantidad());
        }
        
        System.out.println("✓ Venta completada y guardada.");
    }

    private Pago procesarPago(double totalVenta) {
        System.out.println("\n--- METODOS DE PAGO DISPONIBLES ---");
        System.out.println("1. Tarjeta");
        System.out.println("2. Efectivo");
        System.out.println("3. Transferencia");
        System.out.println("0. Cancelar");
        
        int opcion = inputReader.leerEntero("Seleccione metodo de pago: ");
        
        switch (opcion) {
            case 1:
                return procesarPagoTarjeta(totalVenta);
            case 2:
                return procesarPagoEfectivo(totalVenta);
            case 3:
                return procesarPagoTransferencia(totalVenta);
            case 0:
                System.out.println("Pago cancelado.");
                return null;
            default:
                System.out.println("Opcion no valida.");
                return null;
        }
    }

    private Pago procesarPagoTarjeta(double totalVenta) {
        System.out.println("\n--- PAGO CON TARJETA ---");
        
        String numeroTarjeta = inputReader.leerTexto("Numero de tarjeta (16 digitos): ");
        String nombreTitular = inputReader.leerTexto("Nombre del titular: ");
        String fechaVencimiento = inputReader.leerTexto("Fecha vencimiento (MM/YY): ");
        String cvv = inputReader.leerTexto("CVV (3-4 digitos): ");
        
        if (!PagoValidator.validarTarjeta(numeroTarjeta, nombreTitular, fechaVencimiento, cvv)) {
            System.out.println("Error: Datos de tarjeta invalidos.");
            return null;
        }
        
        Pago pago = new Pago(totalVenta, "Tarjeta");
        pago.setDatosTarjeta(numeroTarjeta, nombreTitular, fechaVencimiento);
        
        if (pago.procesarPago()) {
            return pago;
        }
        return null;
    }

    private Pago procesarPagoEfectivo(double totalVenta) {
        System.out.println("\n--- PAGO EN EFECTIVO ---");
        System.out.println("Total a pagar: $" + String.format("%.2f", totalVenta));
        
        double montoEntregado = inputReader.leerDecimal("Monto entregado: $");
        
        if (!PagoValidator.validarEfectivo(totalVenta, montoEntregado)) {
            System.out.println("Error: Monto insuficiente.");
            return null;
        }
        
        double vuelto = PagoValidator.calcularVuelto(totalVenta, montoEntregado);
        
        Pago pago = new Pago(totalVenta, "Efectivo");
        pago.setDatosEfectivo(montoEntregado, vuelto);
        
        if (pago.procesarPago()) {
            return pago;
        }
        return null;
    }

    private Pago procesarPagoTransferencia(double totalVenta) {
        System.out.println("\n--- PAGO POR TRANSFERENCIA ---");
        System.out.println("Total a transferir: $" + String.format("%.2f", totalVenta));
        
        String banco = inputReader.leerTexto("Banco origen: ");
        String numeroCuenta = inputReader.leerTexto("Numero de cuenta (10+ digitos): ");
        String cedula = inputReader.leerTexto("Cedula/RUC: ");
        
        if (!PagoValidator.validarTransferencia(banco, numeroCuenta, cedula)) {
            System.out.println("Error: Datos de transferencia invalidos.");
            return null;
        }
        
        Pago pago = new Pago(totalVenta, "Transferencia");
        pago.setDatosTransferencia(banco, numeroCuenta, cedula);
        
        if (pago.procesarPago()) {
            System.out.println("\n✓ Por favor confirme la transferencia bancaria.");
            System.out.println("  Banco destino: UrbanClass Bank");
            System.out.println("  Cuenta: 1234567890");
            String confirmar = inputReader.leerTexto("¿Transferencia realizada? (si/no): ");
            if (confirmar.equalsIgnoreCase("si")) {
                return pago;
            }
        }
        return null;
    }

    private void verHistorialTickets() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- HISTORIAL DE TICKETS ---");
            System.out.println("1. Ver todos los tickets");
            System.out.println("2. Ver ticket especifico");
            System.out.println("0. Volver");
            
            int opcion = inputReader.leerEntero("Seleccione una opcion: ");
            
            switch (opcion) {
                case 1:
                    TicketHelper.mostrarTickets();
                    break;
                case 2:
                    int numTickets = TicketHelper.contarTickets();
                    if (numTickets == 0) {
                        System.out.println("\nNo hay tickets registrados.");
                    } else {
                        int numero = inputReader.leerEntero("Ingrese numero de ticket (1-" + numTickets + "): ");
                        TicketHelper.mostrarTicketPorNumero(numero);
                    }
                    break;
                case 0:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}

