package logica;

import java.io.*;
import java.lang.*;

import gui.SudokuGUI;

public class Sudoku {

	private Bloque tablero[][];
	private int cant_bloques;
	private SudokuGUI app;

	public Sudoku(String path) {
		this.cant_bloques = 3;
		//tablero = new Bloque[cant_bloques][cant_bloques];
		int[][] filematrix = procesar_archivo(path);

		/*for (int i=0; i < cant_bloques; i++) {
			for (int j=0; j < cant_bloques; j++) {

				this.tablero[i][j].armarCeldas();
			}
		}*/
	}

	private int[][] procesar_archivo(String path) {
		int [][] matrix = new int[9][9];
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
						matrix[f][i] = numero;
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
			
			for (int o=0; o < matrix.length; o++) {
				for (int u = 0; u < matrix[0].length; u++) {
					System.out.print(matrix[o][u] + " ");
				}
				System.out.println();
			}
			
			System.out.println(valida);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return matrix;
	}

	private boolean chequear_validez(int[][] matrix, int fila, int columna) {
		return (restriccionFila(matrix, fila, columna))
				&& (restriccionColumna(matrix, fila, columna))
				&& (restriccionSubseccion(matrix, fila, columna));
	}

	private boolean restriccionSubseccion(int[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		int subseccionFilaStart = (fila/3)* 3;
		int subseccionFilaEnd = subseccionFilaStart + 3;
		
		int subseccionColumnaStart = (columna/3)*3;
		int subseccionColumnaEnd = subseccionColumnaStart + 3;
		
		for (int r=subseccionFilaStart; r < subseccionFilaEnd; r++) {
			for (int c=subseccionColumnaStart; c < subseccionColumnaEnd; c++) {
				if (matrix[fila][columna] == matrix[r][c])
					apariciones++;
			}
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
	}

	private boolean restriccionColumna(int[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		for (int i=0; i < matrix[0].length && (apariciones == 1); i++) {
			if (matrix[fila][columna] == matrix[i][columna])
				apariciones++;
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
	}

	private boolean restriccionFila(int[][] matrix, int fila, int columna) {
		boolean cumple = true;
		int apariciones = 0;
		
		for (int i=0; i < matrix.length && (apariciones == 1); i++) {
			if (matrix[fila][columna] == matrix[fila][i])
				apariciones++;
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
	}

	public int cantBloques() {
		return cant_bloques;
	}

	public Bloque getBloque(int i, int j) {
		return this.tablero[i][j];
	}
	
	public static void main(String args[]) {
		Sudoku game = new Sudoku("/home/nicolas/Escritorio/sudoku");
		
		
		
		
	}
}
