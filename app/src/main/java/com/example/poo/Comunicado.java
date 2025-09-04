package com.example.poo;

import java.io.Serializable;

/**
 * La clase {@code Comunicado} representa un comunicado emitido por un área específica,
 * que puede ser un evento o un anuncio.
 * Contiene información como título, descripción, tipo, área, audiencia,
 * así como detalles adicionales como fecha, lugar, urgencia e imagen asociada.
 *
 * <p>Implementa las interfaces:
 * <ul>
 *   <li>{@link Comparable} para permitir la comparación de comunicados según su título.</li>
 *   <li>{@link Serializable} para permitir la serialización de los objetos.</li>
 * </ul>
 *
 * @author Paola Reyes
 */
public class Comunicado implements Comparable<Comunicado>, Serializable {
    private int id;
    private String tipo;
    private String area;
    private String titulo;
    private String audiencia;
    private String descripcion;
    private String imagen;
    private String lugar;
    private String fecha;
    private String urgencia;
    /**
     * Constructor que inicializa todos los atributos de la clase {@code Comunicado}.
     *
     * @param id          Identificador único del comunicado
     * @param tipo        Tipo de comunicado (ej. evento, anuncio)
     * @param area        Área responsable del comunicado
     * @param titulo      Título del comunicado
     * @param audiencia   Público objetivo del comunicado
     * @param descripcion Descripción o contenido del comunicado
     * @param imagen      Ruta o referencia a la imagen asociada
     * @param lugar       Lugar del evento (si aplica)
     * @param fecha       Fecha del evento (si aplica)
     * @param urgencia    Nivel de urgencia del comunicado (para anuncios)
     */
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

    /**
     * Obtiene el título del comunicado.
     *
     * @return Título del comunicado
     */
    public String getTitulo() {return titulo;}
    /**
     * Obtiene la descripción del comunicado.
     *
     * @return Descripción del comunicado
     */
    public String getDescripcion() { return descripcion; }
    /**
     * Obtiene la imagen asociada al comunicado.
     *
     * @return Imagen del comunicado
     */
    public String getImagen() { return imagen; }

    /**
     * Obtiene la fecha del comunicado (para eventos).
     *
     * @return Fecha del comunicado
     */
    public String getFecha() { return fecha; }
    /**
     * Obtiene el tipo del comunicado (ej. evento, anuncio).
     *
     * @return Tipo de comunicado
     */
    public String getTipo() { return tipo; }
    /**
     * Compara dos comunicados en función de su título (ignora mayúsculas/minúsculas).
     *
     * @param otro Comunicado con el que se desea comparar
     * @return un valor negativo, cero o positivo según el título de este comunicado
     *         sea menor, igual o mayor que el del otro comunicado
     */

    @Override
    public int compareTo(Comunicado otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }
}