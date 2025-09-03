package com.example.poo;

import java.io.Serializable;

public class Comunicado implements Comparable<Comunicado>, Serializable {
    private int id;
    private String tipo;
    private String area;
    private String titulo;
    private String audiencia;
    private String descripcion;
    private String imagen;
    private String lugar;  // evento
    private String fecha;  // evento
    private String urgencia; // anuncio

    public Comunicado(int id, String tipo, String area, String titulo, String audiencia, String descripcion, String imagen, String lugar, String fecha, String urgencia) {
        this.id = id;
        this.tipo = tipo;
        this.area = area;
        this.titulo = titulo;
        this.audiencia = audiencia;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.lugar = lugar;
        this.fecha = fecha;
        this.urgencia = urgencia;
    }

    // Getters
    public String getTitulo() {return titulo;}
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public String getFecha() { return fecha; }
    public String getTipo() { return tipo; }

    @Override
    public int compareTo(Comunicado otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }
}