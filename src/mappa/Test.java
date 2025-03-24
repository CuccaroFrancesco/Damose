package mappa;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.Button;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		setTitle("Damose App Trasporti");
		setMinimumSize(new Dimension(1080, 720));
		setSize(new Dimension(1080, 720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setSize(new Dimension(1080, 720));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrCercaLineaO = new JTextArea();
		txtrCercaLineaO.setBackground(new Color(255, 255, 255));
		txtrCercaLineaO.setAlignmentY(1.0f);
		txtrCercaLineaO.setAlignmentX(1.0f);
		txtrCercaLineaO.setFont(new Font("Arial Nova", Font.PLAIN, 13));
		txtrCercaLineaO.setText("Cerca linea o fermata..");
		txtrCercaLineaO.setBounds(335, 51, 145, 20);
		contentPane.add(txtrCercaLineaO);
		
		Button button = new Button("Q");
		button.setFont(new Font("Arial Nova", Font.PLAIN, 12));
		button.setBounds(297, 51, 32, 20);
		contentPane.add(button);
	}
}
