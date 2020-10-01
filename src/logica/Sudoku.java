package logica;

import java.io.*;

public class Sudoku {

	private Bloque tablero[][];
	private int cant_bloques;

	public Sudoku(String path) {
		try {
			this.cant_bloques = 3;
			tablero = new Bloque[cant_bloques][cant_bloques];
			BufferedReader file = new BufferedReader(new FileReader(path));
			String linea = "";

			for (int i=0; i < cant_bloques && (linea = file.readLine()) != null ; i++) {
				for (int j=0; j < cant_bloques; j++) {

					this.tablero[i][j].armarCeldas();
				}
			}
		}
		catch (IOException err) {

		}

	}

	public int cantBloques() {
		return cant_bloques;
	}

	public Bloque getBloque(int i, int j) {
		return this.tablero[i][j];
	}
}
