package com.example.clima;

public class PronosticoTiempo {
    private String lugar;
    private int imagen;
    private double maxima;
    private double minima;
    private double actual;
    private String descripcion;

    public PronosticoTiempo(String lugar, int imagen, double maxima, double minima) {
        this.lugar = lugar;
        this.imagen = imagen;
        this.maxima = maxima;
        this.minima = minima;
    }
    public PronosticoTiempo()
    {

    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public double getMaxima() {
        return maxima;
    }

    public void setMaxima(double maxima) {
        this.maxima = maxima;
    }

    public double getMinima() {
        return minima;
    }

    public void setMinima(double minima) {
        this.minima = minima;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
