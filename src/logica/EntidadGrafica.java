package logica;

import javax.swing.ImageIcon;

public class EntidadGrafica {

	private ImageIcon grafico;
	private String[] imagenes;
	
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
	
	public void actualizar(int indice) {
		if (indice >= 0 && indice < this.imagenes.length) {
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice]));
			this.grafico.setImage(imageIcon.getImage());
		}
	}
	
	
	public ImageIcon getGrafico() {
		return grafico;
	}
	
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
}
