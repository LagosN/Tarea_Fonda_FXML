package org.example.tarea_fonda_fxml.model;

public class BebidaSinAlcohol extends Bebida {
    private int azucarPorLitro;

    public BebidaSinAlcohol(String nombre, int volumenMl, int stock, int azucarPorLitro) {
        super(nombre, volumenMl, stock);
        this.setAzucarPorLitro(azucarPorLitro);
    }

    public int getAzucarPorLitro() {
        return azucarPorLitro;
    }

    public void setAzucarPorLitro(int azucarPorLitro) {
        this.azucarPorLitro = azucarPorLitro;
    }

    @Override
    public double calcularPrecio() {

        double precio;

        if (getAzucarPorLitro() > 80){
            precio = (int) (2000*1.1);
            return precio;
        }
        precio = 2000;
        return precio;
    }

    @Override
    public String toString() {
        return "Tipo: Bebida Sin Alcohol | Nombre: " + getNombre()
                + " | Volumen: " + getVolumenMl() + " ml"
                + " | Stock: " + getStock()
                + " | Azúcar: " + getAzucarPorLitro() + " g/L"
                + " | Precio: $" + (int) calcularPrecio();
    }

    @Override
    public String getTipo() {
        return "Sin Alcohol";
    }

}
