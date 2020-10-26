package gui;

import javax.swing.ImageIcon;

/**
 * Clase representativa del componente gráfico asociado a cada celda del tablero.
 * @author Nicolás González.
 *
 */
public class EntidadGrafica {

	private ImageIcon grafico;
	private String[] imagenes;
	
	/**
	 * Inicializa el componente gráfico estableciendo su ícono y las direcciones de las imágenes.
	 */
	public EntidadGrafica() {
		this.grafico = new ImageIcon();
		this.imagenes = new String[] {
				"/img/1.png",
				"/img/2.png",
				"/img/3.png",
				"/img/4.png",
				"/img/5.png",
				"/img/6.png",
				"/img/7.png",
				"/img/8.png",
				"/img/9.png",
		};
	}
	
	/**
	 * Actualiza la entidad gráfica asociada a la celda editable.
	 * @param indice Valor con el cual se va a actualizar la entidad.
	 */
	public void actualizar(int indice) {
		if (indice >= 0 && indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	/**
	 * Devuelve el icono de la entidad gráfica corriente.
	 * @return
	 */
	public ImageIcon getGrafico() {
		return grafico;
	}
	
	/**
	 * Devuelve el conjunto de direcciones URL correspondientes a la ubicación de las imágenes disponibles para la entidad gráfica
	 * @return Arreglo de direcciones URL en formato String.
	 */
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	/**
	 * Setea el ícono de la entidad gráfica.
	 * @param grafico
	 */
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
}
