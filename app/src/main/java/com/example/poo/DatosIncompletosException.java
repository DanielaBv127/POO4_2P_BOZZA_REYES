package com.example.poo;
/**
 * Excepción personalizada que se lanza cuando los datos requeridos
 * para una operación no están completos o faltan valores obligatorios.
 *
 * <p>Hereda de {@link Exception}, por lo que es una excepción verificada
 * (checked exception), lo cual significa que debe declararse en la firma
 * de los métodos con {@code throws} o manejarse con un bloque {@code try-catch}.</p>
 *
 * @author Daniela Bozza
 * @version 1.0
 * @since 2025-09-04
 */
public class DatosIncompletosException extends Exception {
  /**
   * Crea una nueva excepción {@code DatosIncompletosException} con un mensaje específico.
   *
   * @param mensaje Mensaje que describe el motivo de la excepción
   */
  public DatosIncompletosException(String mensaje) {
    super(mensaje);
  }
}
