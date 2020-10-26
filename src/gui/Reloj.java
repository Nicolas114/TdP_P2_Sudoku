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

@SuppressWarnings("serial")
public class Reloj extends JPanel{
	
	private Timer timer;
	private int segundos, minutos, horas;
	private ImageIcon[] iconos;
	private JLabel decenaH, unidadH, decenaM, unidadM, decenaS, unidadS, dospuntos;
	
	public Reloj() {
		this.iconos = new ImageIcon[11];
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black));
		JLabel dp1 = new JLabel();
		JLabel dp2 = new JLabel();
		decenaH = new JLabel();
		unidadH = new JLabel();
		decenaM = new JLabel();
		unidadM = new JLabel();
		decenaS = new JLabel();
		unidadS = new JLabel();
		dospuntos = new JLabel();
		horas = 0;		
		minutos = 0;
		segundos = 0;
		
		setImagenes();
		organizar(dp1, dp2);
		timer = new Timer (1000, (ActionListener) new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if(segundos<59) {
					segundos++;
				}	
				else if(minutos<59){
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
	 * Agrega los diferentes labels al panel y se definen sus vistas gráficas.
	 * @param paneldosP1 - Label al que se le setea una imagen.
	 * @param paneldosP2 - Label al que se le setea una imagen.
	 */
	public void organizar(JLabel paneldosP1, JLabel paneldosP2) {	
		this.setLayout(new GridLayout(0, 8, 0, 0));
		
		decenaH.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		this.add(decenaH);
		unidadH.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(unidadH);
		
		paneldosP1.setIcon(iconos[10]);
		paneldosP1.setSize(30, 30);
		redimensionar(paneldosP1, iconos[10]);
		paneldosP1.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(paneldosP1);
		
		decenaM.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(decenaM);
		unidadM.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(unidadM);
		
		paneldosP2.setIcon(iconos[10]);
		paneldosP2.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(paneldosP2);
		
		decenaS.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(decenaS);
		unidadS.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
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
	
	public void setImagenes() {		
		for(int i=0; i<11; i++) {
			iconos[i] = new ImageIcon(getClass().getResource("/img/"+i+"c.png"));
		}
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