package org.example.tarea_fonda_fxml.model;

public class BebidaAlcoholica extends Bebida implements ConsumoResponsable {
    public static final int LIMITE_UNIDADES_POR_CLIENTE = 3;

    private double gradosAlcohol;
    private boolean certificada;
    private boolean ventaRestringida;

    public BebidaAlcoholica(String nombre, int volumenMl, int stock,
                            double gradosAlcohol, boolean certificada) {
        this(nombre, volumenMl, stock, gradosAlcohol, certificada, false);
    }

    public BebidaAlcoholica(String nombre, int volumenMl, int stock) {
        super(nombre, volumenMl, stock);
    }


    public BebidaAlcoholica(String nombre, int volumenMl, int stock,
                            double gradosAlcohol, boolean certificada,
                            boolean ventaRestringida) {
        super(nombre, volumenMl, stock);
        this.setGradosAlcohol(gradosAlcohol);
        this.setCertificada(certificada);
        this.setVentaRestringida(ventaRestringida);
    }

    public double getGradosAlcohol() {
        return gradosAlcohol;
    }

    public void setGradosAlcohol(double gradosAlcohol) throws  IllegalArgumentException {
        if (gradosAlcohol < 0.5 || gradosAlcohol > 45) {
            throw new IllegalArgumentException("Los grados deben estar entre 0.5 y 45");
        }
        this.gradosAlcohol = gradosAlcohol;
    }

    public boolean isVentaRestringida() {
        return ventaRestringida;
    }

    public void setVentaRestringida(boolean ventaRestringida) {
        this.ventaRestringida = ventaRestringida;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    @Override
    public boolean tieneVentaRestringida() {
        return ventaRestringida;
    }
    


    @Override
    public void restringirVenta() {
        setVentaRestringida(true);
    }

    @Override
    public double calcularPrecio() {
        return certificada ? 3500 : 4200;
    }

    @Override
    public String toString() {
        String mensaje;
        mensaje = "Tipo: Bebida Alcoholica | Nombre: " + getNombre()
                + " | Volumen: " + getVolumenMl() + " ml"
                + " | Stock: " + getStock()
                + " | Grados: " + getGradosAlcohol()
                + " | Certificada: " + (isCertificada() ? "Si" : "No")
                + " | Venta restringida: " + (tieneVentaRestringida() ? "Si" : "No")
                + " | Precio: $" + (int) calcularPrecio();
        return mensaje;
    }

    @Override
    public boolean superaLimite(int unidades) {
        return unidades > LIMITE_UNIDADES_POR_CLIENTE;
    }

    @Override
    public String getTipo() {
        return "Alcoholica";
    }


}
