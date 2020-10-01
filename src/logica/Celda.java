package logica;

public class Celda {
	
	private Integer valor;
	private EntidadGrafica entidad_graf;
	
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
}
