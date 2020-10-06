package logica;

import java.io.*;
import java.util.Random;
import gui.SudokuGUI;

public class Sudoku {

	private Celda tablero[][];
	private int cant_filas;
	private SudokuGUI app;

	public Sudoku() {
		String path = "/home/nicolas/Escritorio/sudoku";
		this.cant_filas = 9;
		tablero = new Celda[cant_filas][cant_filas];
		Celda[][] filematrix = procesar_archivo(path);
		
		for (int i=0; i < cant_filas; i++) {
			int contador = 0;
			for (int j=0; j < cant_filas; j++) {
				Random rand = new Random();
				int value = rand.nextInt(2);
				tablero[i][j] = new Celda();
				
				if (contador < 4 && value == 0) {
					contador++;
					tablero[i][j].setValor(0);
				}
				else {
					tablero[i][j].setValor(filematrix[i][j].getValor());
				}
			}
		}
		
		//BORRAR >>>>> MUESTRA LA MATRIZ >>>> BORRAR
		for (int o=0; o < tablero.length; o++) {
			for (int u = 0; u < tablero[0].length; u++) {
				System.out.print(tablero[o][u].getValor() + " ");
			}
			System.out.println();
		}
		
		System.out.println();
	}

	private Celda[][] procesar_archivo(String path) {
		Celda [][] matrix = new Celda[9][9];
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
						matrix[f][i] = new Celda(); 
						matrix[f][i].setValor(numero);
					}
					
					f++;
				}
			}
			
			boolean valida = true;
			
			for (int fila=0; valida && fila < matrix.length; fila++) {
				for (int columna=0; valida && columna < matrix[0].length; columna++) {
					valida = chequear_validez(matrix, fila, columna);
				}
			}
			
			file.close();
			System.out.println("Es valida: " + valida); //BORRAR
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		
		return matrix;
	}

	private boolean chequear_validez(Celda[][] matrix, int fila, int columna) {
		return (restriccionFila(matrix, fila, columna))
				&& (restriccionColumna(matrix, fila, columna))
				&& (restriccionSubseccion(matrix, fila, columna));
	}

	private boolean restriccionSubseccion(Celda[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		/*int subseccionFilaStart = (fila/3)* 3;
		int subseccionFilaEnd = subseccionFilaStart + 3;
		
		int subseccionColumnaStart = (columna/3)*3;
		int subseccionColumnaEnd = subseccionColumnaStart + 3;
		*/
		
		//same as above but simplified
		
		int r = fila - fila%3;
		int c = columna - columna%3;
		
		for (int i=r; i < r+3; i++) {
			for (int j=c; j < c; j++) {
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
		
		for (int i=0; i < matrix[0].length && (apariciones <= 1); i++) {
			if (matrix[fila][columna].getValor().equals(matrix[i][columna].getValor()))
				apariciones++;
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
	}

	private boolean restriccionFila(Celda[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		for (int i=0; i < matrix.length && (apariciones <= 1); i++) {
			if (matrix[fila][columna].getValor().equals(matrix[fila][i].getValor()))
				apariciones++;
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
}
