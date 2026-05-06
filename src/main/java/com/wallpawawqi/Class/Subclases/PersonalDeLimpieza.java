package com.wallpawawqi.Class.Subclases;

import com.wallpawawqi.Class.Empleado;

public class PersonalDeLimpieza extends Empleado {
    private String turno;

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public PersonalDeLimpieza() {
    } 

    public void limpiarArea(){
        // TODO implementar limpieza de áreas
    }

    public void desinfectarSuperficies(){
        // TODO implementar desinfección de superficies
    }
}
