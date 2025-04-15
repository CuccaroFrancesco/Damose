package damose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class UserPanel extends JPanel {
	
	// Costruzione del pannello utente (login, registrazione e profilo)
	public UserPanel() {
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		
		// Scritta "Ospite"
		JLabel titolo = new JLabel("Ospite");
				
		titolo.setForeground(new Color(255, 255, 255));
		titolo.setFont(new Font("Arial Nova", Font.BOLD, 24));
		titolo.setFocusable(false);
				
		titolo.setHorizontalAlignment(SwingConstants.CENTER);
		titolo.setBounds(0, 180, 400, 50);
				
		this.add(titolo);
		
		
		// Pulsante per l'accesso (con account già esistente)
		JButton btnAccedi = new JButton("Accedi");
		
		btnAccedi.setBounds(75, 300, 250, 45);
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnAccedi);
		
		
		// Pulsante per la registrazione (creazione di un nuovo account)
		JButton btnRegistrati = new JButton("Registrati");
		
		btnRegistrati.setBounds(75, 380, 250, 45);
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		this.add(btnRegistrati);
		
// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputNome
		JLabel lblNome = new JLabel("Nome");
							
		lblNome.setBounds(75, 190, 95, 35);
		lblNome.setVisible(false);
							
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblNome.setFocusable(false);
							
		this.add(lblNome);
				
				
		// Casella di testo per inserire il vero nome per la registrazione
		JTextField inputNome = new JTextField();
					
		inputNome.setBounds(75, 220, 250, 35);
		inputNome.setVisible(false);
					
		this.add(inputNome);
			
// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputUsername
		JLabel lblUsername = new JLabel("Username");
			
		lblUsername.setBounds(75, 280, 95, 30);
		lblUsername.setVisible(false);
			
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblUsername.setFocusable(false);
			
		this.add(lblUsername);
		
		
		// Casella di testo per inserire lo username dell'account 
		JTextField inputUsername = new JTextField();
		
		inputUsername.setBounds(75, 310, 250, 35);
		inputUsername.setVisible(false);
		
		this.add(inputUsername);
			
// ---------------------------------------------------------------------------------------------
			
		// Label per la casella di testo inputPassword
		JLabel lblPassword = new JLabel("Password");
			
		lblPassword.setBounds(75, 370, 95, 30);
		lblPassword.setVisible(false);
			
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblPassword.setFocusable(false);
			
		this.add(lblPassword);
		
		
		// Casella di testo per inserire la password dell'account 
		JPasswordField inputPassword = new JPasswordField();
		
		inputPassword.setBounds(75, 400, 250, 35);
		inputPassword.setVisible(false);
		
		this.add(inputPassword);
		
// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputConfirmPassword
		JLabel lblConfermaPassword = new JLabel("Conferma password");
									
		lblConfermaPassword.setBounds(75, 460, 200, 30);
		lblConfermaPassword.setVisible(false);
									
		lblConfermaPassword.setForeground(Color.WHITE);
		lblConfermaPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblConfermaPassword.setFocusable(false);
									
		this.add(lblConfermaPassword);
		
		
		// Casella di testo per confermare la password dell'account da creare
		JPasswordField inputConfermaPassword = new JPasswordField();
						
		inputConfermaPassword.setBounds(75, 490, 250, 35);
		inputConfermaPassword.setVisible(false);
						
		this.add(inputConfermaPassword);
		
// ---------------------------------------------------------------------------------------------
			
		// Label che visualizza un eventuale errore nell'inserimento del nome
		JLabel erroreNome = new JLabel();
		
		erroreNome.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreNome.setForeground(new Color(255, 94, 94));
		erroreNome.setVerticalAlignment(SwingConstants.TOP);
		
		erroreNome.setVisible(false);
		erroreNome.setBounds(75, 258, 250, 20);
		
		this.add(erroreNome);
		
		
		// Label che visualizza un eventuale errore nell'inserimento dello username
		JLabel erroreUsername = new JLabel();
		
		erroreUsername.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreUsername.setForeground(new Color(255, 94, 94));
		erroreNome.setVerticalAlignment(SwingConstants.TOP);
		
		erroreUsername.setVisible(false);
		erroreUsername.setBounds(75, 345, 250, 20);
		
		this.add(erroreUsername);
		
		
		// Label che visualizza un eventuale errore nell'inserimento della password
		JLabel errorePassword = new JLabel();
		
		errorePassword.setFont(new Font("Arial Nova", Font.BOLD, 12));
		errorePassword.setForeground(new Color(255, 94, 94));
		erroreNome.setVerticalAlignment(SwingConstants.TOP);
		
		errorePassword.setVisible(false);
		errorePassword.setBounds(75, 435, 250, 20);
		
		this.add(errorePassword);
		
		
		// Label che visualizza un eventuale errore nella conferma della password
		JLabel erroreConfermaPassword = new JLabel();
			
		erroreConfermaPassword.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreConfermaPassword.setForeground(new Color(255, 94, 94));
		erroreNome.setVerticalAlignment(SwingConstants.TOP);
		
		erroreConfermaPassword.setVisible(false);
		erroreConfermaPassword.setBounds(75, 525, 250, 20);
		
		this.add(erroreConfermaPassword);
		
		
		// Label che viene visualizzata se la registrazione è stata effettuata correttamente
		JLabel registrazioneEffettuata = new JLabel();
		
		registrazioneEffettuata.setFont(new Font("Arial Nova", Font.BOLD, 12));
		registrazioneEffettuata.setForeground(new Color(0, 255, 0));
		registrazioneEffettuata.setVisible(false);
		
		this.add(registrazioneEffettuata);
		
// ---------------------------------------------------------------------------------------------
		
		// Pulsante per confermare le credenziali di login
		JButton btnConfermaLogin = new JButton("Conferma");
		
		btnConfermaLogin.setBounds(100, 480, 200, 45);
		btnConfermaLogin.setVisible(false);
		btnConfermaLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnConfermaLogin);
		
		
		// Pulsante per confermare i dati della registrazione
		JButton btnConfermaRegistr = new JButton("Conferma");
			
		btnConfermaRegistr.setBounds(100, 600, 200, 45);
		btnConfermaRegistr.setVisible(false);
		btnConfermaRegistr.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
		this.add(btnConfermaRegistr);
		
// ---------------------------------------------------------------------------------------------
		
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
		
		
// ---------------------------------------------------------------------------------------------
		
		
		// Funzionalità per il pulsante btnAccedi (rende visibile la schermata di login)
		btnAccedi.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
					
				titolo.setText("Login");
				titolo.setBounds(0, 200, 400, 50);
				btnBack.setVisible(true);
					
				btnAccedi.setVisible(false);
				btnRegistrati.setVisible(false);
					
				inputUsername.setVisible(true);
				inputPassword.setVisible(true);
				lblUsername.setVisible(true);
				lblPassword.setVisible(true);
					
				btnConfermaLogin.setVisible(true);
			}
		});
		
		
		// Funzionalità per il pulsante btnRegistrati (rende visibile la schermata di registrazione)
		btnRegistrati.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
					
				titolo.setText("Registrazione");
				titolo.setBounds(0, 140, 400, 50);
				btnBack.setVisible(true);
					
				btnAccedi.setVisible(false);
				btnRegistrati.setVisible(false);
					
				lblNome.setVisible(true);
				inputNome.setVisible(true);
					
				lblUsername.setVisible(true);
				inputUsername.setVisible(true);
					
				lblPassword.setVisible(true);
				inputPassword.setVisible(true);
					
				lblConfermaPassword.setVisible(true);
				inputConfermaPassword.setVisible(true);
					
				btnConfermaRegistr.setVisible(true);
			}
		});
		
		
		// Funzionalità per il pulsante btnBack (torna indietro, rendendo visibile il profilo Ospite o Utente)
		btnBack.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e) {
						
				titolo.setText("Ospite");
				titolo.setBounds(0, 200, 400, 50);
				btnBack.setVisible(false);
				erroreNome.setVisible(false);
				erroreUsername.setVisible(false);
				errorePassword.setVisible(false);
				erroreConfermaPassword.setVisible(false);
						
				btnAccedi.setVisible(true);
				btnRegistrati.setVisible(true);
						
				lblNome.setVisible(false);
				inputNome.setText("");
				inputNome.setVisible(false);
						
				lblUsername.setVisible(false);
				inputUsername.setText("");
				inputUsername.setVisible(false);
						
				lblPassword.setVisible(false);
				inputPassword.setText("");
				inputPassword.setVisible(false);
						
				lblConfermaPassword.setVisible(false);
				inputConfermaPassword.setText("");
				inputConfermaPassword.setVisible(false);
						
				btnConfermaRegistr.setVisible(false);
				btnConfermaLogin.setVisible(false);
			}
		});
				
				
// ---------------------------------------------------------------------------------------------
				
				
		// Funzionalità per il pulsante "Conferma registrazione"
		btnConfermaRegistr.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
					
				String newNome = inputNome.getText().trim();
				String newUsername = inputUsername.getText().trim();
				String newPassword = inputPassword.getText().trim();
				String newConfermaPassword = inputConfermaPassword.getText().trim();
					
				try {
						
					String resultNome = Registrazione.checkNome(newNome);
					String resultUsername = Registrazione.checkUsername(newUsername);
					String resultPassword = Registrazione.checkPassword(newPassword);
					String resultConfermaPassword = Registrazione.checkConfermaPassword(newPassword, newConfermaPassword);
					
					if (!resultNome.equals("Verificata.")) {
						erroreNome.setText(resultNome);
						erroreNome.setVisible(true);
					} else {
						erroreNome.setVisible(false);
					}
					
					if (!resultUsername.equals("Verificata.")) {
						erroreUsername.setText(resultUsername);
						erroreUsername.setVisible(true);
					} else {
						erroreUsername.setVisible(false);
					}
					
					if (!resultPassword.equals("Verificata.")) {
						errorePassword.setText(resultPassword);
						errorePassword.setVisible(true);
					} else {
						errorePassword.setVisible(false);
					}
					
					if (!resultConfermaPassword.equals("Verificata.")) {
						erroreConfermaPassword.setText(resultConfermaPassword);
						erroreConfermaPassword.setVisible(true);
					} else {
						erroreConfermaPassword.setVisible(false);
					}
					
					if (resultNome.equals("Verificata.") && resultUsername.equals("Verificata.") && resultPassword.equals("Verificata.") && resultConfermaPassword.equals("Verificata.")) {
						registrazioneEffettuata.setText(Registrazione.addUser(newNome, newUsername, newPassword, newConfermaPassword));
					}
							
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
			
				
		// Funzionalità per il pulsante "Conferma Login"
		btnConfermaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: 
			}
		});
	}
}