package logica;

public class Celda {
	
	private Integer valor;
	private EntidadGrafica entidad_graf;
	
	public Celda() {
		this.valor = null;
		this.entidad_graf = new EntidadGrafica();
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return this.entidad_graf;
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	public int getCantElementos() {
		return entidad_graf.getImagenes().length;
	}

	public void actualizar() {
		entidad_graf.actualizar(this.valor-1);
		
		if (this.valor != null && this.valor < this.getCantElementos()) {
			this.valor++;
		}
		else {
			this.valor = 0;
		}
	}
}
