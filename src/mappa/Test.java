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
	private JTextField txtCercaLineaO;

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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1064, 38);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtCercaLineaO = new JTextField();
		txtCercaLineaO.setBounds(442, 6, 302, 22);
		txtCercaLineaO.setText("Cerca linea o fermata...");
		panel.add(txtCercaLineaO);
		txtCercaLineaO.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setPreferredSize(new Dimension(25, 25));
		btnNewButton.setBounds(407, 6, 25, 23);
		panel.add(btnNewButton);
	}
}
