package logica;

public class Celda {
	
	private Integer valor;
	private EntidadGrafica entidad_graf;
	private boolean editable;
	private int fila;
	private int columna;
	
	public Celda(int fila, int columna) {
		this.valor = null;
		this.entidad_graf = new EntidadGrafica();
		this.editable = true;
		this.setFila(fila);
		this.setColumna(columna);
	}
	
	public void actualizar() {
		
		if (this.valor != null && this.valor < this.getCantElementos()) {
			this.valor++;
		}
		else {
			this.valor = 1;
		}
		
		entidad_graf.actualizar(this.valor-1);
		
	}

	public void actualizar_error() {
		if (this.valor != null && this.valor < this.getCantElementos())
			this.valor++;
		else
			this.valor = 1;
		
		entidad_graf.actualizar(this.valor-1 + 8);
	}

	public boolean equals(Celda c) {
		return this.valor == c.getValor();
	}
	
	public int getCantElementos() {
		return entidad_graf.getImagenes().length;
	}
	
	public int getColumna() {
		return columna;
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return this.entidad_graf;
	}

	
	public int getFila() {
		return fila;
	}

	public Integer getValor() {
		return valor;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public void setFila(int fila) {
		this.fila = fila;
	}

	public void setGrafica(EntidadGrafica g) {
		this.entidad_graf = g;
	}
	
	public void setValor(Integer valor) {
		
		if (valor != null && valor-1 < this.getCantElementos()) {
			this.valor = valor;
			this.entidad_graf.actualizar(this.valor-1);
		}
	}
}
