package gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Sudoku;

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
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		juego = new Sudoku("");
		
		panel_juego = new JPanel();
		panel_juego.setLayout(new GridLayout(juego.cantBloques(), 0, 0, 0));
		panel_juego.setBounds(30, 30, 530, 400);
		contentPane.add(panel_juego);
		
		
	}

}
