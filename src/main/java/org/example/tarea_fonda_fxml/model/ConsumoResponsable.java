package org.example.tarea_fonda_fxml.model;

public interface ConsumoResponsable
{ public boolean tieneVentaRestringida();
    public void restringirVenta();
    public boolean superaLimite(int unidades);
}
