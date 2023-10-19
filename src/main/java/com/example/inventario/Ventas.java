package com.example.inventario;

import java.util.Date;

public class Ventas {
    String mes;
    int entradasTotales;
    int salidasTotales;

    Ventas(){

    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getEntradasTotales() {
        return entradasTotales;
    }

    public void setEntradasTotales(int entradasTotales) {
        this.entradasTotales = entradasTotales;
    }

    public int getSalidasTotales() {
        return salidasTotales;
    }

    public void setSalidasTotales(int salidasTotales) {
        this.salidasTotales = salidasTotales;
    }
}
