package com.wallpawawqi.Class.Subclases;

import com.wallpawawqi.Class.Empleado;

public class Cocinero extends Empleado {
    private String especialidad;

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Cocinero() {
    }
 
    public void prepararPlatillo(){
        // TODO implementar preparación de platillos
    }

    public void serguirReceta(){
        // TODO implementar seguimiento de recetas
    }
}
