package logica;

import java.io.*;
import java.util.Random;

public class Sudoku {

	private Celda tablero[][];
	private int cant_filas;
	private boolean errores[][];

	public Sudoku() throws InvalidFileException {
		String path = "sudoku.txt";
		this.cant_filas = 9;
		this.errores = new boolean[cant_filas][cant_filas];
		tablero = new Celda[cant_filas][cant_filas];
		procesar_archivo(path);
		
		System.out.println("Despues de procesar archivo");
		mostrarTablero(); //BORRAR
		
		for (int i=0; i < cant_filas; i++) {
			int contador = 0;
			for (int j=0; j < cant_filas; j++) {
				Random rand = new Random();
				int value = rand.nextInt(2);
				errores[i][j] = false;
				if (contador < 1 && value == 0) { //CAMBIAR A CONTADOR < 6
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
		mostrarErrores();
	}
	
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
		//this.buscar_errores(c.getFila(), c.getColumna());
		//this.buscar_errores_panel(c.getFila(), c.getColumna());
	}
	
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
			
			for (int i = 0; i < errores.length; i++) {
				if (i != fila_error) {
					if (tablero[i][col_error].getValor().equals(tablero[fila_error][col_error].getValor())) {
						atado = true;
					}
				}
			}
			
			if (!atado)
				this.errores[fila_error][col_error] = false;
		}
		
		//para la parte de columnas
		apariciones = 0;
		for (int i = 0; i < errores.length; i++) {
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
			
			for (int i = 0; i < errores.length; i++) {
				if (i != col_error) {
					if (tablero[fila_error][i].getValor().equals(tablero[fila_error][col_error].getValor())) {
						atado = true;
					}
				}
			}
			
			if (!atado)
				this.errores[fila_error][col_error] = false;
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
						for (int k = 0; k < errores.length && !atado; k++) {
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
							
							for (int k = 0; k < errores.length && !atado; k++) {
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

	private void buscar_errores(int fila, int columna) {
		Integer valor = tablero[fila][columna].getValor();
		for (int i = 0; i < tablero.length; i++) {
			if (i != columna && tablero[fila][i].getValor() != 0) {
				if (valor.equals(tablero[fila][i].getValor())) {
					errores[fila][columna] = true;
					errores[fila][i] = true;
				}
			}
		}
		
		for (int j = 0; j < tablero.length; j++) {
			if (j != fila && tablero[j][columna].getValor() != 0) {
				if (valor.equals(tablero[j][columna].getValor())) {
					errores[j][columna] = true;
					errores[fila][columna] = true;
				}
			}
		}
		
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
	
	public int cantFilas() {
		return cant_filas;
	}

	public int cantFilasSubpanel() {
		return this.cant_filas/3;
	}
	
	public boolean compararCelda(Celda c1, Celda c2) {
		return c1.getValor().equals(c2.getValor());
	}

	public boolean esEditable(Celda c) {
		return c.isEditable();
	}
	
	public Celda getCelda(int i, int j) {
		return this.tablero[i][j];
	}

	public Celda[][] obtenerTablero() {
		return tablero;
	}

	public boolean[][] getErrores(){
		return this.errores;
	}

	public void mostrarErrores() {
		for (int i=0; i < this.errores.length; i++) {
			for (int j=0; j < this.errores[0].length; j++) {
				if (errores[i][j])
					System.out.print("t ");
				else
					System.out.print("f ");
			}
			System.out.println();
		}
	}

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

	public void mostrarTablero() {
		for (int o=0; o < tablero.length; o++) {
			for (int u = 0; u < tablero[0].length; u++) {
				System.out.print(tablero[o][u].getValor() + " ");
			}
			System.out.println();
		}
		
		System.out.println("-------");
	}

	private void procesar_archivo(String path) throws InvalidFileException {
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
			throw new InvalidFileException();
		} catch (NumberFormatException e) {
			throw new InvalidFileException();
		}
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
		
		for (int i=0; i < matrix.length && apariciones < 2; i++) {
			if (matrix[fila][columna].getValor().equals(matrix[fila][i].getValor())) {
				
				apariciones++;
			}
		}
		
		if (apariciones > 1)
			cumple = false;
		
		return cumple;
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

	private boolean chequear_validez(Celda[][] matrix, int fila, int columna) {
		return (restriccionFila(matrix, fila, columna))
				&& (restriccionColumna(matrix, fila, columna))
				&& (restriccionSubseccion(matrix, fila, columna));
	}
}
