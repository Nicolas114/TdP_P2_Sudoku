package logica;

/**
 * Clase representativa del error provocado al momento de trabajar con un archivo inválido. 
 * @author Nicolás González.
 *
 */
@SuppressWarnings("serial")
public class InvalidFileException extends Exception {

	/**
	 * Inicializa la excepción con un mensaje predefinido por la clase.
	 */
	public InvalidFileException() {
		super("El archivo es inválido.");
	}
}
