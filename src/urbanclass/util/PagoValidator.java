package urbanclass.util;

public class PagoValidator {
    
    public static boolean validarTarjeta(String numeroTarjeta, String nombreTitular, String fechaVencimiento, String cvv) {
        // Validar número de tarjeta (16 dígitos)
        if (numeroTarjeta == null || !numeroTarjeta.matches("\\d{16}")) {
            System.out.println("Error: Número de tarjeta debe tener 16 dígitos.");
            return false;
        }
        
        // Validar nombre del titular (no vacío)
        if (nombreTitular == null || nombreTitular.trim().isEmpty()) {
            System.out.println("Error: Nombre del titular es requerido.");
            return false;
        }
        
        // Validar fecha de vencimiento (MM/YY)
        if (fechaVencimiento == null || !fechaVencimiento.matches("\\d{2}/\\d{2}")) {
            System.out.println("Error: Fecha debe ser MM/YY.");
            return false;
        }
        
        // Validar CVV (3-4 dígitos)
        if (cvv == null || !cvv.matches("\\d{3,4}")) {
            System.out.println("Error: CVV debe tener 3 o 4 dígitos.");
            return false;
        }
        
        return true;
    }
    
    public static boolean validarTransferencia(String banco, String numeroCuenta, String cedula) {
        // Validar banco (no vacío)
        if (banco == null || banco.trim().isEmpty()) {
            System.out.println("Error: Nombre del banco es requerido.");
            return false;
        }
        
        // Validar número de cuenta (mínimo 10 dígitos)
        if (numeroCuenta == null || !numeroCuenta.matches("\\d{10,}")) {
            System.out.println("Error: Número de cuenta debe tener al menos 10 dígitos.");
            return false;
        }
        
        // Validar cédula (formato básico)
        if (cedula == null || cedula.trim().isEmpty()) {
            System.out.println("Error: Cédula es requerida.");
            return false;
        }
        
        return true;
    }
    
    public static boolean validarEfectivo(double monto, double montoEntregado) {
        // Validar que el monto entregado sea suficiente
        if (montoEntregado < monto) {
            System.out.println("Error: Monto entregado insuficiente.");
            return false;
        }
        
        return true;
    }
    
    public static double calcularVuelto(double monto, double montoEntregado) {
        return montoEntregado - monto;
    }
}
