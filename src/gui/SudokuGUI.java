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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Celda;
import logica.Sudoku;

@SuppressWarnings("serial")
public class SudokuGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel_juego;
	private Sudoku juego;

	/**
	 * Launch the application.
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

	/**
	 * Create the frame.
	 */
	public SudokuGUI() {
		setResizable(false);
		
		juego = new Sudoku();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 597);
		setTitle("Sudoku");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		panel_juego = new JPanel();
		panel_juego.setBackground(new Color(59,63,72));
		panel_juego.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		panel_juego.setBounds(30, 30, 538, 430);
		panel_juego.setLayout(new GridLayout(juego.cantFilas(), 0, 0, 0));
		contentPane.add(panel_juego);
		for (int i=0; i < juego.cantFilas(); i++) {
			for (int j=0; j < juego.cantFilas(); j++) {
				Celda c = juego.getCelda(i, j);
				JLabel celdalabel = new JLabel();
				ImageIcon grafico = c.getEntidadGrafica().getGrafico();
				
				celdalabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				
				if (i == 3 || i == 6)
					celdalabel.setBorder(BorderFactory.createMatteBorder(4, 1, 1, 1, Color.WHITE));
				if (j == 3 || j == 6)
					celdalabel.setBorder(BorderFactory.createMatteBorder(1, 4, 1, 1, Color.WHITE));
				if ( (i == 3 && j == 3) || (i == 3 && j == 6) || (i == 6 && j == 3) || (i == 6 && j == 6))
					celdalabel.setBorder(BorderFactory.createMatteBorder(4, 4, 1, 1, Color.WHITE));
				
				juego.accionar(c);
				
				panel_juego.add(celdalabel);

				celdalabel.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						celdalabel.setIcon(grafico);
						redimensionar(celdalabel, grafico);
					}
				});
				
				celdalabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						juego.accionar(c);
						redimensionar(celdalabel,grafico);
					}
				});
			}
		}
	}
	
	private void redimensionar(JLabel label, ImageIcon grafico) {
		Image image = grafico.getImage();
		if (image != null) {  
			Image nueva = image.getScaledInstance(label.getWidth(), label.getHeight(),  java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(nueva);
			label.repaint();
		}
	}
}
