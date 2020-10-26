package logica;

import gui.EntidadGrafica;

/**
 * Clase representativa de una celda del tablero del juego. Almacena un valor, la posición en que
 * se encuentra, una entidad gráfica que lo representa gráficamente, y la cualidad de ser editable (o no).
 * @author Nicolás González
 *
 */
public class Celda {
	
	private Integer valor;
	private EntidadGrafica entidad_graf;
	private boolean editable;
	private int fila;
	private int columna;
	
	/**
	 * Inicializa la celda con la fila y columna en que se encontrará.
	 * @param fila Fila en la que se ubicará la celda.
	 * @param columna Columna en la que se ubicará la celda.
	 */
	public Celda(int fila, int columna) {
		this.valor = null;
		this.entidad_graf = new EntidadGrafica();
		this.editable = true;
		this.fila= fila;
		this.columna = columna;
	}
	
	/**
	 * Actualiza el valor de la celda corriente. Dicho valor variará en el 
	 * intervalo entero [1, 9] después de actualizado (no puede volver a ser 0).
	 * 
	 */
	public void actualizar() {
		
		if (this.editable) {
			
			if (this.valor != null && this.valor < this.getCantElementos()) {
				this.valor++;
			}
			else {
				this.valor = 1;
			}
			
			//actualiza su correspondiente entidad gráfica.
			entidad_graf.actualizar(this.valor-1);
		}
	}

	/**
	 * Determina si la celda corriente es igual a la celda parametrizada.
	 * @param c Celda que se quiere comparar.
	 * @return true si ambas celdas son iguales.
	 */
	public boolean equals(Celda c) {
		//se optó por comparar su valor.
		return this.valor.equals(c.getValor());
	}
	
	/**
	 * Devuelve la cantidad de objetos
	 * @return
	 */
	public int getCantElementos() {
		return entidad_graf.getImagenes().length;
	}
	
	/**
	 * Devuelve la ubicación de la celda en las columnas.
	 * @return
	 */
	public int getColumna() {
		return columna;
	}

	/**
	 * Devuelve la entidad gráfica asociada a la celda corriente.
	 * @return La entidad gráfica de la celda.
	 */
	public EntidadGrafica getEntidadGrafica() {
		return this.entidad_graf;
	}

	/**
	 * Devuelve la ubicación de la celda en las filas.
	 * @return
	 */
	public int getFila() {
		return fila;
	}

	public Integer getValor() {
		return valor;
	}

	public boolean isEditable() {
		return editable;
	}

	/**
	 * Setea la cualidad editable de la celda corriente. 
	 * @param editable Valor para la permisión de edición de la celda. Si es false, la celda no puede ser actualizada.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Setea la entidad gráfica asociada a la celda corriente por la parametrizada.
	 * @param g Entidad gráfica nueva.
	 */
	public void setGrafica(EntidadGrafica g) {
		this.entidad_graf = g;
	}
	
	/**
	 * Modifica el valor corriente de la celda.
	 * @param valor Valor que se va a almacenar.
	 */
	public void setValor(Integer valor) {
		
		if (valor != null && valor-1 < this.getCantElementos()) {
			this.valor = valor;
			this.entidad_graf.actualizar(this.valor-1);
		}
	}
}
