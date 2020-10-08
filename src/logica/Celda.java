package logica;

public class Celda {
	
	private Integer valor;
	private EntidadGrafica entidad_graf;
	private boolean editable;
	
	public Celda() {
		this.valor = null;
		this.entidad_graf = new EntidadGrafica();
		this.editable = true;
	}
	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public EntidadGrafica getEntidadGrafica() {
		return this.entidad_graf;
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public void setValor(Integer valor) {
		
		if (valor != null && valor-1 < this.getCantElementos()) {
			this.valor = valor;
			this.entidad_graf.actualizar(this.valor-1);
		}
	}
	
	public int getCantElementos() {
		return entidad_graf.getImagenes().length;
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
	
	public void setGrafica(EntidadGrafica g) {
		this.entidad_graf = g;
	}
}
