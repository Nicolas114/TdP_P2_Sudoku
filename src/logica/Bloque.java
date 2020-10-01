package logica;

import java.util.Random;

public class Bloque {
	
	private Celda celdas[][];
	private int cant_filas;
	
	public Bloque() {
		cant_filas = 3;
		celdas = new Celda[cant_filas][cant_filas];
	}
	
	public Celda getCelda(int i, int j) {
		return celdas[i][j];
	}
	
	public int cantFilas() {
		return this.cant_filas;
	}

	public void armarCeldas() {
		
		for (int i=0; i < cant_filas; i++) {
			for (int j=0; j < cant_filas; j++) {
				
				this.celdas[i][j] = new Celda();
			}
		}
	}
}
