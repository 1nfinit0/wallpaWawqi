package com.wallpawawqi.Class.Subclases;
import com.wallpawawqi.Class.Empleado;

public class Mozo extends Empleado {
    private String mesaAsignada;

    public String getMesaAsignada() { return mesaAsignada; }
    public void setMesaAsignada(String mesaAsignada) { this.mesaAsignada = mesaAsignada; }

    public Mozo() {
    }  

    public void tomarOrden(){
        // TODO implementar toma de órdenes
    }
    public void registrarVenta(){
        // TODO implementar registro de ventas
    }
    public void agregarPlatillo(){
        // TODO implementar adición de platillos al pedido
    }
    public void agregarBebida(){
        // TODO implementar adición de bebidas al pedido
    }
    public void entregarBoleta(){
        // TODO implementar entrega de boleta al cliente
    }
}
