package com.example.inventario;

import java.util.Date;

public class Movimientos {

    int id;
    String nombre;
    Date fecha;
    String tipo;
    int cantidad;
    String ubicacion;


public Movimientos(int id, String nombre, Date fecha, String tipo, int cantidad, String ubicacion){
  this.id = id;
  this.nombre = nombre;
  this.tipo = tipo;
  this.fecha = fecha;
  this.cantidad = cantidad;
  this.ubicacion = ubicacion;
}


    public Movimientos(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
