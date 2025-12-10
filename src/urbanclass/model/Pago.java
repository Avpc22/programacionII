package urbanclass.model;

public class Pago {
    private double monto;
    private String metodoPago;
    private boolean procesado;
    
    // Datos de tarjeta
    private String numeroTarjeta;
    private String nombreTitular;
    private String fechaVencimiento;
    
    // Datos de transferencia
    private String banco;
    private String numeroCuenta;
    private String cedula;
    
    // Datos de efectivo
    private double montoEntregado;
    private double vuelto;

    public Pago(double monto, String metodoPago) {
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.procesado = false;
        this.vuelto = 0;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public boolean isProcessado() {
        return procesado;
    }

    // Setters para tarjeta
    public void setDatosTarjeta(String numeroTarjeta, String nombreTitular, String fechaVencimiento) {
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.fechaVencimiento = fechaVencimiento;
    }

    // Setters para transferencia
    public void setDatosTransferencia(String banco, String numeroCuenta, String cedula) {
        this.banco = banco;
        this.numeroCuenta = numeroCuenta;
        this.cedula = cedula;
    }

    // Setters para efectivo
    public void setDatosEfectivo(double montoEntregado, double vuelto) {
        this.montoEntregado = montoEntregado;
        this.vuelto = vuelto;
    }

    public boolean validarPago() {
        if (monto <= 0) {
            return false;
        }
        
        String metodo = metodoPago.toLowerCase();
        return metodo.equals("tarjeta") || 
               metodo.equals("efectivo") || 
               metodo.equals("transferencia");
    }

    public boolean procesarPago() {
        if (!validarPago()) {
            System.out.println("Error: Pago invalido. Valide el monto y metodo de pago.");
            return false;
        }
        
        procesado = true;
        System.out.println("\n✓ Pago procesado exitosamente");
        System.out.println("  Monto: $" + String.format("%.2f", monto));
        System.out.println("  Metodo: " + metodoPago);
        
        if (metodoPago.equalsIgnoreCase("efectivo") && vuelto > 0) {
            System.out.println("  Vuelto: $" + String.format("%.2f", vuelto));
        }
        
        return true;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getBanco() {
        return banco;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getCedula() {
        return cedula;
    }

    public double getMontoEntregado() {
        return montoEntregado;
    }

    public double getVuelto() {
        return vuelto;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "monto=" + String.format("%.2f", monto) +
                ", metodoPago='" + metodoPago + '\'' +
                ", procesado=" + procesado +
                '}';
    }
}
