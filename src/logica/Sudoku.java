package logica;

import java.io.*;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.EntidadGrafica;

/**
 * Clase que representa el juego Sudoku; está encargada de la lógica de dicho juego, esto es,
 * el control del cumplimiento de las reglas del juego, la validación de los datos, etc.
 * @author Nicolás González
 * @see {@link SudokuGUI}
 *
 */
public class Sudoku {

	private Celda tablero[][];
	private int cant_filas;
	private boolean errores[][];
	private static Logger logger;

	/**
	 * Inicializa el juego con su respectiva configuración.
	 * @throws InvalidFileException En caso que el archivo contenga caracteres inválidos, esté mal organizado, entre otros.
	 */
	public Sudoku() throws InvalidFileException {
		
		if (logger == null) {
			logger = Logger.getLogger(Sudoku.class.getName());

			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.WARNING);
			logger.addHandler(hnd);

			logger.setLevel(Level.WARNING);
			
			Logger rootLogger = logger.getParent();
			for (Handler h : rootLogger.getHandlers()) {
				h.setLevel(Level.OFF);
			}
		}
		
		InputStream in = Sudoku.class.getClassLoader().getResourceAsStream("logica/sudoku.txt");
		this.cant_filas = 9;
		this.errores = new boolean[cant_filas][cant_filas];
		tablero = new Celda[cant_filas][cant_filas];
		procesar_archivo(in);
		
		for (int i=0; i < cant_filas; i++) {
			int contador = 0;
			for (int j=0; j < cant_filas; j++) {
				Random rand = new Random();
				int value = rand.nextInt(2);
				errores[i][j] = false;
				if (contador < 6 && value == 0) {
					contador++;
					tablero[i][j].setValor(0);
					tablero[i][j].setGrafica(new EntidadGrafica());
					tablero[i][j].setEditable(true);
				}
			}
		}
	}
	
	/**
	 * Realiza una acción a partir de una celda dada.
	 * @param c Celda a la cual se van a aplicar las acciones.
	 */
	public void accionar(Celda c) {
		for (int i = 0; i < errores.length; i++) {
			for (int j = 0; j < errores.length; j++) {
				descartar_errores(tablero[i][j]);				
			}
		}
		
		c.actualizar();
		this.errores[c.getFila()][c.getColumna()] = false;
		
		for (int i = 0; i < errores.length; i++) {
			for (int j = 0; j < errores.length; j++) {
				this.buscar_errores(i, j);
			}
		}
	}
	
	/**
	 * Devuelve la cantidad de filas del juego.
	 * @return Cantidad de filas actuales del juego.
	 */
	public int cantFilas() {
		return cant_filas;
	}

	/**
	 * Devuelve la cantidad de filas que conforman un subpanel del juego. Por convención, independientemente de la cantidad de filas,
	 * un subpanel se construirá siempre dividiendo por 3 a la cantidad de filas.
	 * @return Cantidad de filas conformante de los sub-paneles del juego.
	 */
	public int cantFilasSubpanel() {
		return this.cant_filas/3;
	}
	
	/**
	 * Compara dos celdas y determina si son iguales.
	 * @param c1 Primer celda a comparar.
	 * @param c2 Segunda celda a comparar
	 * @return true si ambas celdas son iguales, false en caso contrario.
	 */
	public boolean compararCelda(Celda c1, Celda c2) {
		return c1.getValor().equals(c2.getValor());
	}

	/**
	 * Corrobora si la celda parametrizada es editable.
	 * @param c Celda a corroborar.
	 * @return true si la celda es editable, false en caso contrario.
	 */
	public boolean esEditable(Celda c) {
		return c.isEditable();
	}
	
	/**
	 * Devuelve una celda a partir de los índices parametrizados.
	 * @param i Índice correspondiente a la fila.
	 * @param j Índice correspondiente a la columna.
	 * @return La celda ubicada en el tablero en la posición (i, j).
	 */
	public Celda getCelda(int i, int j) {
		return this.tablero[i][j];
	}

	/**
	 * Devuelve una matriz de Celdas correspondiente al tablero del juego.
	 * @return El tablero del juego.
	 */
	public Celda[][] obtenerTablero() {
		return tablero;
	}

	/**
	 * Devuelve una matriz booleana donde están especificadas las posiciones de las celdas que
	 * están incumpliendo alguna regla del juego.
	 * @return El tablero de errores del juego.
	 */
	public boolean[][] getErrores(){
		return this.errores;
	}

	public void mostrarErrores() {
		for (int i=0; i < cant_filas; i++) {
			for (int j=0; j < this.errores[0].length; j++) {
				if (errores[i][j])
					System.out.print("t ");
				else
					System.out.print("f ");
			}
			System.out.println();
		}
	}

	/**
	 * Evalúa si todas las celdas del tablero cumplen con las reglas del juego.
	 * @return true si el juego está completado correctamente.
	 */
	public boolean juego_valido() {
		boolean valido = true;
		for (int i=0; i < tablero.length && valido; i++) {
			for (int j=0; j < tablero[0].length && valido; j++) {
//				valido = chequear_validez(tablero, i, j);
//				valido = tablero[i][j].getValor() != 0;
				valido = errores[i][j] == false && tablero[i][j].getValor() != 0;
			}
		}
		
		return valido;
	}

	/**
	 * Muestra el tablero por consola.
	 */
	public void mostrarTablero() {
		for (int o=0; o < tablero.length; o++) {
			for (int u = 0; u < tablero[0].length; u++) {
				System.out.print(tablero[o][u].getValor() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Examina el tablero a partir de una dada celda para corroborar si siguen existiendo errores.
	 * @param celda Celda a partir de la cual se examinará el tablero.
	 */
	private void descartar_errores(Celda celda) {
		int fila = celda.getFila();
		int columna = celda.getColumna();
		int apariciones = 0;
		int fila_error = 0;
		int col_error = 0;
		
		//para la parte de filas
		for (int i = 0; i < errores.length; i++) {
				if (i != columna) {
					if (tablero[fila][columna].getValor().equals(tablero[fila][i].getValor())) {
						apariciones++;
						fila_error = fila;
						col_error = i;
					}
				}
		}
		
		if (apariciones == 1) {
			boolean atado = false;
			
			for (int i = 0; i < cant_filas; i++) {
				if (i != fila_error) {
					if (tablero[i][col_error].getValor().equals(tablero[fila_error][col_error].getValor())) {
						atado = true;
					}
				}
			}
			
			if (!atado) {
				this.errores[fila_error][col_error] = false;
			}
		}
		
		//para la parte de columnas
		apariciones = 0;
		for (int i = 0; i < cant_filas; i++) {
			if (i != fila) {
				if (tablero[i][columna].getValor().equals(tablero[fila][columna].getValor())) {
					apariciones++;
					fila_error = i;
					col_error = columna;
				}
			}
		}
		
		if (apariciones == 1) {
			boolean atado = false;
			
			for (int i = 0; i < cant_filas; i++) {
				if (i != col_error) {
					if (tablero[fila_error][i].getValor().equals(tablero[fila_error][col_error].getValor())) {
						atado = true;
					}
				}
			}
			
			if (!atado) {
				this.errores[fila_error][col_error] = false;
			}
		}
		
		//para los paneles
		apariciones = 0;
		int r = fila - fila%this.cantFilasSubpanel();
		int c = columna - columna%this.cantFilasSubpanel();
		
		for (int i=r; i < r+3; i++) {
			for (int j=c; j < c+3; j++) {
				if (i != fila && j != columna && tablero[i][j].getValor() != 0) {
					if (tablero[i][j].getValor().equals(tablero[fila][columna].getValor())) {
						fila_error = i;
						col_error = j;
						
						//chequear si está atada a otro elem en su fila o col
						boolean atado = false;
						
						//fila
						for (int k = 0; k < cant_filas && !atado; k++) {
							if (k != fila_error && tablero[k][col_error].getValor() != 0) {
								if (tablero[k][col_error].getValor().equals(tablero[fila_error][col_error].getValor())) {
									atado = true;
								}
							}
						}
						
						if (!atado) {
							errores[fila_error][col_error] = true;
						}
						
						if (true) {
							atado = false;
							
							for (int k = 0; k < cant_filas && !atado; k++) {
								if (k != col_error && tablero[fila_error][k].getValor() != 0) {
									if (tablero[fila_error][k].getValor().equals(tablero[fila_error][col_error].getValor())) {
										atado = true;
									}
								}
							}
							
							if (!atado) {
								errores[fila_error][col_error] = false;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Busca errores en el tablero a partir de una dada fila y columna. 
	 * @param fila Fila a partir de la cual se buscarán errores.
	 * @param columna Columna a partir de la cual se buscarán errores.
	 */
	private void buscar_errores(int fila, int columna) {
		Integer valor = tablero[fila][columna].getValor();
		
		//para las filas
		for (int i = 0; i < cant_filas; i++) {
			if (i != columna && tablero[fila][i].getValor() != 0) {
				if (valor.equals(tablero[fila][i].getValor())) {
					errores[fila][columna] = true;
					errores[fila][i] = true;
				}
			}
		}
		
		//para las columnas
		for (int j = 0; j < cant_filas; j++) {
			if (j != fila && tablero[j][columna].getValor() != 0) {
				if (valor.equals(tablero[j][columna].getValor())) {
					errores[j][columna] = true;
					errores[fila][columna] = true;
				}
			}
		}
		
		//para los paneles
		int r = fila - fila%this.cantFilasSubpanel();
		int c = columna - columna%this.cantFilasSubpanel();
		
		for (int i=r; i < r+3; i++) {
			for (int j=c; j < c+3; j++) {
				if (i != fila && j != columna && tablero[i][j].getValor() != 0) {
					if (tablero[i][j].getValor().equals(tablero[fila][columna].getValor())) {
						errores[i][j] = true;
						errores[fila][columna] = true;
					}
				}
			}
		}
	}

	/**
	 * Procesa el archivo requerido para la inicialización del juego.
	 * @param in Recurso del archivo.
	 * @throws InvalidFileException Si el archivo es inválido (contiene caracteres inválidos, mala organización de columnas, espacios, etc).
	 */
	private void procesar_archivo(InputStream in) throws InvalidFileException {
		try {
			InputStreamReader inr = new InputStreamReader(in);
			BufferedReader file = new BufferedReader(inr);
			String linea = "";
			String[] arreglo_linea = null;
			int f = 0;
			
			while ((linea = file.readLine()) != null) {
				if (!linea.isBlank()) {
					arreglo_linea = linea.split(" ");
					
					for (int i=0; i < arreglo_linea.length; i++) {
						int numero = Integer.parseInt((arreglo_linea[i]));
						tablero[f][i] = new Celda(f, i); 
						tablero[f][i].setValor(numero);
						tablero[f][i].setEditable(false);
					}
					
					f++;
				}
			}
			
			file.close();
			
			boolean valida = juego_valido();
			
			if (!valida) {
				throw new InvalidFileException();
			}
			
		} catch (IOException e) {
			logger.severe("ARCHIVO ERRÓNEO: NO SE PUDO LOCALIZAR/ABRIR.");
			throw new InvalidFileException();
		} catch (NumberFormatException e) {
			logger.severe("ARCHIVO INVÁLIDO: Contiene caracteres no numéricos");
			throw new InvalidFileException();
		}
	}
}
