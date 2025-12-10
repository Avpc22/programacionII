package urbanclass.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TicketHelper {
    private static final String ARCHIVO_TICKETS = "datos/tickets.txt";

    public static List<String> cargarTickets() {
        List<String> tickets = new ArrayList<>();
        
        if (!Files.exists(Paths.get(ARCHIVO_TICKETS))) {
            return tickets;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_TICKETS))) {
            StringBuilder ticketActual = new StringBuilder();
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                ticketActual.append(linea).append("\n");
                
                // Si es línea de separación final, es fin de ticket
                if (linea.contains("==") && ticketActual.toString().split("\n").length > 5) {
                    tickets.add(ticketActual.toString());
                    ticketActual = new StringBuilder();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar tickets: " + e.getMessage());
        }
        
        return tickets;
    }

    public static int contarTickets() {
        return cargarTickets().size();
    }

    public static void mostrarTickets() {
        List<String> tickets = cargarTickets();
        
        if (tickets.isEmpty()) {
            System.out.println("\nNo hay tickets registrados.");
            return;
        }
        
        System.out.println("\n" + StringUtil.repeat("=", 60));
        System.out.println("HISTORIAL DE TICKETS (" + tickets.size() + " tickets)");
        System.out.println(StringUtil.repeat("=", 60));
        
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println("\n--- TICKET #" + (i + 1) + " ---");
            System.out.println(tickets.get(i));
        }
        
        System.out.println(StringUtil.repeat("=", 60) + "\n");
    }

    public static void mostrarTicketPorNumero(int numero) {
        List<String> tickets = cargarTickets();
        
        if (numero < 1 || numero > tickets.size()) {
            System.out.println("Ticket no encontrado. Total de tickets: " + tickets.size());
            return;
        }
        
        System.out.println("\n" + StringUtil.repeat("=", 60));
        System.out.println("TICKET #" + numero);
        System.out.println(StringUtil.repeat("=", 60));
        System.out.println(tickets.get(numero - 1));
        System.out.println(StringUtil.repeat("=", 60) + "\n");
    }
}
