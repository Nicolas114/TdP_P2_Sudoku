package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Clase representativa del componente gráfico de un cronómetro. Maneja tanto la parte gráfica
 * como la parte lógica.
 * @author Nicolás González
 *
 */
@SuppressWarnings("serial")
public class Reloj extends JPanel{
	
	private Timer timer;
	private ImageIcon[] iconos;
	private int segundos, minutos, horas;
	private JLabel decenaH, unidadH, decenaM, unidadM, decenaS, unidadS;
	
	/**
	 * Inicializa el reloj y configura sus atributos.
	 */
	public Reloj() {
		this.iconos = new ImageIcon[11];
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
		JLabel dospuntos1 = new JLabel();
		JLabel dospuntos2 = new JLabel();
		decenaH = new JLabel();
		unidadH = new JLabel();
		decenaM = new JLabel();
		unidadM = new JLabel();
		decenaS = new JLabel();
		unidadS = new JLabel();
		horas = 0;		
		minutos = 0;
		segundos = 0;
		
		organizarImagenes();
		organizar(dospuntos1, dospuntos2);
		
		timer = new Timer (1000, (ActionListener) new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
				if(segundos<59) {
					segundos++;
				}	
				else if(minutos<59) {
					minutos++;
					segundos=0;
				}
				else {
					horas++;
					minutos=0;
					segundos=0;
				}
				
				unidadS.setIcon(iconos[segundos%10]);
				redimensionar(unidadS, iconos[segundos%10]);
				decenaS.setIcon(iconos[segundos/10]);
				redimensionar(decenaS, iconos[segundos/10]);
				unidadM.setIcon(iconos[minutos%10]);
				redimensionar(unidadM, iconos[minutos%10]);
				decenaM.setIcon(iconos[minutos/10]);
				redimensionar(decenaM, iconos[minutos/10]);
				unidadH.setIcon(iconos[horas%10]);
				redimensionar(unidadH, iconos[horas%10]);
				decenaH.setIcon(iconos[horas/10]);
				redimensionar(decenaH, iconos[horas/10]);
			}
		});
	}
	
	/**
	 * Organiza los paneles de la gráfica de los "dos puntos" (delimitador para horas-minutos y minutos-segundos).
	 * @param paneldosP1 Componente gráfico correspondiente al primer panel de "dos puntos".
	 * @param paneldosP2 Componente gráfico correspondiente al segundo panel de "dos puntos".
	 */
	public void organizar(JLabel paneldosP1, JLabel paneldosP2) {	
		this.setLayout(new GridLayout(0, 8, 0, 0));
		
		decenaH.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		this.add(decenaH);
		unidadH.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		this.add(unidadH);
		
		paneldosP1.setIcon(iconos[10]);
		paneldosP1.setSize(30, 30);
		redimensionar(paneldosP1, iconos[10]);
		paneldosP1.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		this.add(paneldosP1);
		
		decenaM.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		this.add(decenaM);
		unidadM.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		this.add(unidadM);
		
		paneldosP2.setIcon(iconos[10]);
		paneldosP2.setSize(30, 30);
		paneldosP2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		this.add(paneldosP2);
		
		decenaS.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
		this.add(decenaS);
		unidadS.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		this.add(unidadS);
	}
	
	/**
	 * Inicia la contabilización del tiempo.
	 */
	public void iniciar() {
		timer.start();
	}
	
	/**
	 * Frena el cronómetro.
	 */
	public void detener() {
		timer.stop();
	}
	
	/**
	 * Organiza las imágenes del cronómetro.
	 */
	public void organizarImagenes() {
		
		for(int i=0; i<this.iconos.length; i++) {
			iconos[i] = new ImageIcon(getClass().getResource("/img/"+i+"c.png"));
		}
	}
	
	/**
	 * Devuelve la cantidad de segundos.
	 * @return Los segundos corrientes.
	 */
	public int getSegundos() {
		return segundos;
	}
	
	/**
	 * Devuelve la cantidad de minutos.
	 * @return Los minutos corrientes.
	 */
	public int getMinutos() {
		return minutos;
	}
	
	/**
	 * Devuelve la cantidad de horas.
	 * @return Las horas corrientes.
	 */
	public int getHoras() {
		return horas;
	}
	
	
	private void redimensionar(JLabel label, ImageIcon grafico) {
		Image imagen = grafico.getImage();
		if (imagen != null) {  
			Image nueva = imagen.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(nueva);
			label.repaint();
		}
	}
}