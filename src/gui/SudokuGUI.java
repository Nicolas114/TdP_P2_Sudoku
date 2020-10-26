package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Celda;
import logica.InvalidFileException;
import logica.Sudoku;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SudokuGUI extends JFrame {

	/**
	 * Inicia la ejecución del juego.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SudokuGUI frame = new SudokuGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JPanel contentPane;
	private JPanel panel_juego;
	private Sudoku juego;
	private JButton[][] botones;
	private Reloj reloj;

	/**
	 * Crea la ventana donde se ubicarán todos los componentes del juego.
	 */
	public SudokuGUI() {
		setResizable(false);

		try {

			mostrarReglas();
			juego = new Sudoku();
			panel_juego = new JPanel();
			reloj = new Reloj();
			botones = new JButton[juego.cantFilas()][juego.cantFilas()];
			JPanel[][] sub_paneles = new JPanel[juego.cantFilasSubpanel()][juego.cantFilasSubpanel()];

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(250, 150, 600, 489);
			setTitle("Sudoku");
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(null);
			contentPane.setBackground(new Color(59, 63, 72));
			setContentPane(contentPane);
			
			reloj.setBounds(75, 400, 302, 53);

			panel_juego.setBackground(new Color(59,63,72));
			panel_juego.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
			panel_juego.setBounds(30, 30, 400, 360);
			panel_juego.setLayout(new GridLayout(3, 0, 0, 0));
			contentPane.add(panel_juego);
			contentPane.add(reloj);
			
			
			JButton botonLeerReglas = new JButton("Leer reglas");
			botonLeerReglas.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					mostrarReglas();
				}
			});
			botonLeerReglas.setBounds(442, 124, 120, 34);
			contentPane.add(botonLeerReglas);
		
			configurar_subPaneles(sub_paneles);
			
			JButton boton_comienzo = new JButton("Comenzar");
			boton_comienzo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iniciar_juego(sub_paneles);
					reloj.iniciar();
					boton_comienzo.setEnabled(false);
				}
			});
			boton_comienzo.setBounds(442, 82, 120, 30);
			contentPane.add(boton_comienzo);
			
		}
		catch (InvalidFileException err) {
			JOptionPane.showMessageDialog(panel_juego, err.getMessage());
			System.exit(ERROR);
		}
	}
	
	/**
	 * Muestra las reglas asociadas al juego en cuestión.
	 */
	private void mostrarReglas() {
		String informacion = "REGLAS\n" + 
				"Para ganar, usted debe completar todas las casillas vacías con solo uno de los 9 números romanos disponibles.\n"
				+ "Una misma fila no puede contener números repetidos.\n"
				+ "Una misma columna no puede contener números repetidos.\n"
				+ "Un mismo panel no puede contener números repetidos.\n"
				+ "Puede re-leer las reglas en cualquier momento, pero el cronómetro no se detendrá.\n"
				+ "¡Mucha suerte!";
		JOptionPane.showMessageDialog(contentPane, informacion);
	}
	
	/**
	 * Inicia el juego.
	 * @param sub_paneles Paneles donde se ubicarán las celdas así como decoración extra.
	 */
	private void iniciar_juego(JPanel[][] sub_paneles) {
		
		for (int i=0; i < juego.cantFilas(); i++) {
			int n = (int) i/juego.cantFilasSubpanel();

			for (int j=0; j < juego.cantFilas(); j++) {
				int m = (int) j/juego.cantFilasSubpanel();

				Celda c = juego.getCelda(i, j);
				JButton celdabutton = new JButton();
				ImageIcon grafico = c.getEntidadGrafica().getGrafico();

				celdabutton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				celdabutton.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						celdabutton.setIcon(grafico);
						redimensionar(celdabutton, grafico);
					}
				});

				botones[i][j] = celdabutton;
				sub_paneles[n][m].add(celdabutton);

				if (juego.esEditable(c)) {

					celdabutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {

							juego.accionar(c);
							redimensionar(celdabutton,grafico);

							boolean errores[][] = juego.getErrores();
							for (int k = 0; k < juego.cantFilas(); k++) {
								for (int l = 0; l < juego.cantFilas(); l++) {
									if (errores[k][l]) {
										botones[k][l].setBackground(new Color(255,0,0));
									}
									else {
										botones[k][l].setBackground(null);
									}
								}
							}

							//Instrucciones para la consola.
							juego.mostrarTablero();
							System.out.println("---------");
							juego.mostrarErrores();
							System.out.println("---------");

							if (juego.juego_valido()) {
								mostrar_juego_ganado();
							}
						}
					});
				}
			}
		}
	}
	
	/**
	 * Procedimiento encargado de mostrar la información referente a cuando el usuario gana.
	 */
	private void mostrar_juego_ganado() {
		reloj.detener();
		
		for (int k = 0; k < juego.cantFilas(); k++) {
			for (int l = 0; l < juego.cantFilas(); l++) {
				botones[k][l].setEnabled(false);
			}
		}
		
		int seg = reloj.getSegundos();
		int min = reloj.getMinutos();
		int hor = reloj.getHoras();
		
		JOptionPane.showMessageDialog(panel_juego, "Usted ganó con el tiempo de \n"
				+ hor + " horas, " + min + " minutos, " + seg + " segundos.");
		
		System.exit(1);
	}

	/**
	 * Configura los subpaneles donde se ubican las celdas del juego. El procedimiento se enfoca más en una configuración gráfica.
	 * @param sub_paneles Matriz de subpaneles que se va a configurar.
	 */
	private void configurar_subPaneles(JPanel[][] sub_paneles) {
		for (int i=0; i < juego.cantFilasSubpanel(); i++) {
			for (int j=0; j < juego.cantFilasSubpanel(); j++){
				sub_paneles[i][j] = new JPanel();
				sub_paneles[i][j].setLayout(new GridLayout(juego.cantFilasSubpanel(), juego.cantFilasSubpanel(), 0, 0));
				panel_juego.add(sub_paneles[i][j]);
				
				if (i == 0 && j == 0) {
					sub_paneles[i][j].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
				}
				else {
					if (i==0) {
						sub_paneles[i][j].setBorder(BorderFactory.createMatteBorder(4, 0, 4, 4, Color.BLUE));
					}
					else {
						if (j==0) {
							sub_paneles[i][j].setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.BLUE));
						}
						else {
							sub_paneles[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 4, 4, Color.BLUE));
						}
					}
				}
			}
		}		
	}

	/**
	 * Ajusta el tamaño de los componentes gráficos para que se adapten al frame.
	 */
	private void redimensionar(JButton label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image nueva = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(nueva);
			label.repaint();
		}
	}
	
}
