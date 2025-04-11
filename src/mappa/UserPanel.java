package mappa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class UserPanel extends JPanel {
	
	// Costruzione del pannello utente (login, registrazione e profilo)
	public UserPanel() {
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		
		// Pulsante per l'accesso (con account già esistente)
		JButton btnAccedi = new JButton("Accedi");
		
		btnAccedi.setBounds(75, 320, 250, 45);
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnAccedi);
		
		
		// Pulsante per la registrazione (creazione di un nuovo account)
		JButton btnRegistrati = new JButton("Registrati");
		
		btnRegistrati.setBounds(75, 400, 250, 45);
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		this.add(btnRegistrati);
		
		
		// Scritta "Ospite"
		JLabel nomeUtente = new JLabel("Ospite");
		
		nomeUtente.setForeground(new Color(255, 255, 255));
		nomeUtente.setFont(new Font("Arial Nova", Font.BOLD, 24));
		nomeUtente.setFocusable(false);
		
		nomeUtente.setHorizontalAlignment(SwingConstants.CENTER);
		nomeUtente.setBounds(0, 200, 400, 50);
		
		this.add(nomeUtente);
		
		
		// Casella di testo per inserire lo username dell'account a cui si vuole accedere
		JTextField inputUsername = new JTextField();
		
		inputUsername.setBounds(75, 320, 250, 35);
		inputUsername.setVisible(false);
		
		this.add(inputUsername);
		
		
		// Casella di testo per inserire la password dell'account a cui si vuole accedere
		JPasswordField inputPassword = new JPasswordField();
		
		inputPassword.setBounds(75, 400, 250, 35);
		inputPassword.setVisible(false);
		
		this.add(inputPassword);
		
		
		// Pulsante per confermare le credenziali di login
		JButton btnConferma = new JButton("Login");
		
		btnConferma.setBounds(100, 480, 200, 45);
		btnConferma.setVisible(false);
		btnConferma.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnConferma);
		
		
		// Pulsante per tornare indietro
		JButton btnBack = new JButton("Torna indietro");
		
		btnBack.setBounds(10, 11, 185, 23);
		btnBack.setVisible(false);
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnBack.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnBack.setForeground(new Color(255, 255, 255));
		
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
	
		this.add(btnBack);
		
		
		// Label per la casella di testo inputUsername
		JLabel lblUsername = new JLabel("Username");
		
		lblUsername.setBounds(75, 276, 95, 35);
		lblUsername.setVisible(false);
		
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblUsername.setFocusable(false);
		
		this.add(lblUsername);
		
		
		// Label per la casella di testo inputPassword
		JLabel lblPassword = new JLabel("Password");
		
		lblPassword.setBounds(75, 364, 95, 35);
		lblPassword.setVisible(false);
		
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblPassword.setFocusable(false);
		
		this.add(lblPassword);
		
		
		// Funzionalità per il pulsante btnAccedi, rende visibile la schermata di login
		btnAccedi.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				btnBack.setVisible(true);
				
				btnAccedi.setVisible(false);
				btnRegistrati.setVisible(false);
				
				inputUsername.setVisible(true);
				inputPassword.setVisible(true);
				lblUsername.setVisible(true);
				lblPassword.setVisible(true);
				
				btnConferma.setVisible(true);
			}
		});
		
		
		// Funzionalità per il pulsante btnBack, rende visibile la schermata di accesso/registrazione
		btnBack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				btnBack.setVisible(false);
				
				btnAccedi.setVisible(true);
				btnRegistrati.setVisible(true);
				
				inputUsername.setVisible(false);
				inputPassword.setVisible(false);
				lblUsername.setVisible(false);
				lblPassword.setVisible(false);
				
				btnConferma.setVisible(false);
			}
		});
	}
}