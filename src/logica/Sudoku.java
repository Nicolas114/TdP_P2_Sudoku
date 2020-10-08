package logica;

import java.io.*;
import java.util.Random;

public class Sudoku {

	private Celda tablero[][];
	private int cant_filas;

	public Sudoku() {
		String path = "sudoku.txt";
		this.cant_filas = 9;
		tablero = new Celda[cant_filas][cant_filas];
		procesar_archivo(path);
		
		System.out.println("Despues de procesar archivo");
		mostrarTablero(); //BORRAR
		
		for (int i=0; i < cant_filas; i++) {
			int contador = 0;
			for (int j=0; j < cant_filas; j++) {
				Random rand = new Random();
				int value = rand.nextInt(2);
				
				if (contador < 6 && value == 0) {
					contador++;
					tablero[i][j].setValor(0);
					tablero[i][j].setGrafica(new EntidadGrafica());
					tablero[i][j].setEditable(true);
				}
			}
		}
		
		//BORRAR >>>>> MUESTRA LA MATRIZ >>>> BORRAR
		System.out.println("Despues de poner ceros");
		mostrarTablero();
	}

	private void procesar_archivo(String path) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(path));
			String linea = "";
			String[] arreglo_linea = null;
			int f = 0;
			
			while ((linea = file.readLine()) != null) {
				if (!linea.isBlank()) {
					arreglo_linea = linea.split(" ");
					
					for (int i=0; i < arreglo_linea.length; i++) {
						int numero = Integer.parseInt((arreglo_linea[i]));
						tablero[f][i] = new Celda(); 
						tablero[f][i].setValor(numero);
						tablero[f][i].setEditable(false);
					}
					
					f++;
				}
			}
			
			boolean valida = juego_valido();
		//hacer algo con que si no es válido el juego desde el archivo
			
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public boolean juego_valido() {
		boolean valido = true;
		for (int i=0; i < tablero.length && valido; i++) {
			for (int j=0; j < tablero[0].length && valido; j++) {
				valido = chequear_validez(tablero, i, j);
			}
		}
		
		return valido;
	}
	
	private boolean chequear_validez(Celda[][] matrix, int fila, int columna) {
		boolean validez = (restriccionFila(matrix, fila, columna))
					&& (restriccionColumna(matrix, fila, columna))
					&& (restriccionSubseccion(matrix, fila, columna))
					&& (tableroCompleto(matrix));
		
		return validez;
	}

	private boolean tableroCompleto(Celda[][] matrix) {
		boolean hayCeros = false;
		for (int i=0; i < matrix.length && !hayCeros; i++) {
			for (int j=0; j < matrix[0].length && !hayCeros; j++) {
				if (matrix[i][j].getValor() == 0) {
					hayCeros = true;
				}
			}
		}
		
		return !hayCeros;
	}

	private boolean restriccionSubseccion(Celda[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		/*int subseccionFilaStart = (fila/3)* 3;
		int subseccionFilaEnd = subseccionFilaStart + 3;
		
		int subseccionColumnaStart = (columna/3)*3;
		int subseccionColumnaEnd = subseccionColumnaStart + 3;
		*/
		
		//misma lógica que arriba pero simplificado
		
		int r = fila - fila%3;
		int c = columna - columna%3;
		
		for (int i=r; i < r+3 && apariciones < 2; i++) {
			for (int j=c; j < c && apariciones < 2; j++) {
				if (matrix[fila][columna].getValor().equals(matrix[r][c].getValor()))
					apariciones++;
			}
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
	}

	private boolean restriccionColumna(Celda[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		for (int i=0; i < matrix[0].length && (apariciones < 2); i++) {
			if (matrix[fila][columna].getValor().equals(matrix[i][columna].getValor())) {
				apariciones++;
			}
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
	}

	private boolean restriccionFila(Celda[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		for (int i=0; i < matrix.length && (apariciones < 2); i++) {
			if (matrix[fila][columna].getValor().equals(matrix[fila][i].getValor())) {
				apariciones++;
			}
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
	}

	public int cantFilas() {
		return cant_filas;
	}

	public Celda getCelda(int i, int j) {
		return this.tablero[i][j];
	}
	
	public static void main(String args[]) {
		Sudoku game = new Sudoku();
		
	}

	public void accionar(Celda c) {
		c.actualizar();
	}

	public Celda[][] obtenerTablero() {
		return tablero;
	}
	
	public boolean esEditable(Celda c) {
		return c.isEditable();
	}
	
	public void mostrarTablero() {
		for (int o=0; o < tablero.length; o++) {
			for (int u = 0; u < tablero[0].length; u++) {
				System.out.print(tablero[o][u].getValor() + " ");
			}
			System.out.println();
		}
		
		System.out.println("-------");
	}
}
