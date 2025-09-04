package com.example.poo;
/**
 * Excepción personalizada que se lanza cuando las credenciales
 * de un usuario son inválidas (por ejemplo, usuario o contraseña incorrectos).
 *
 * <p>Hereda de {@link RuntimeException}, por lo que es una excepción no verificada
 * (unchecked exception).</p>
 *
 * @author Paola Reyes
 * @version 1.0
 * @since 2025-09-01
 */
public class CredencialesInvalidasException extends RuntimeException {

    /**
     * Crea una nueva excepción {@code CredencialesInvalidasException} con un mensaje específico.
     *
     * @param message Mensaje que describe el motivo de la excepción
     */
    public CredencialesInvalidasException(String message) {
        super(message);
    }
}
