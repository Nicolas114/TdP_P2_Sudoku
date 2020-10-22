package logica;

public class InvalidFileException extends Exception {

	public InvalidFileException() {
		super("El archivo es inválido.");
	}
}
