package damose;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class UserPanel extends JPanel {
	
	private Utente utente;
	private JButton btnAccedi, btnRegistrati, btnConfermaLogin, btnConfermaRegistr, btnBack;
	private JLabel titolo, lblNome, lblCognome, lblUsername, lblPassword, lblConfermaPassword, erroreNome, erroreCognome, erroreUsername, errorePassword, erroreConfermaPassword, registrazioneEffettuata; 
	private JTextField inputNome, inputCognome, inputUsername;
	private JPasswordField inputPassword, inputConfermaPassword;
	
	// Costruzione del pannello utente (login, registrazione e profilo)
	public UserPanel(Utente utente) {
		
		this.utente = utente;
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		
		// Scritta "Ospite"
		titolo = new JLabel("Ospite");
				
		titolo.setForeground(new Color(255, 255, 255));
		titolo.setFont(new Font("Arial Nova", Font.BOLD, 24));
		titolo.setFocusable(false);
				
		titolo.setHorizontalAlignment(SwingConstants.CENTER);
		titolo.setBounds(0, 180, 400, 50);
				
		this.add(titolo);
		
		
		// Pulsante per l'accesso (con account già esistente)
		btnAccedi = new JButton("Accedi");
		
		btnAccedi.setBounds(75, 300, 250, 45);
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnAccedi);
		
		
		// Pulsante per la registrazione (creazione di un nuovo account)
		btnRegistrati = new JButton("Registrati");
		
		btnRegistrati.setBounds(75, 380, 250, 45);
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		this.add(btnRegistrati);
		
// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputNome
		lblNome = new JLabel("Nome");
							
		lblNome.setBounds(75, 150, 95, 35);
		lblNome.setVisible(false);
							
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblNome.setFocusable(false);
							
		this.add(lblNome);
				
				
		// Casella di testo per inserire il nome per la registrazione
		inputNome = new JTextField();
					
		inputNome.setBounds(75, 180, 250, 30);
		inputNome.setVisible(false);
					
		this.add(inputNome);

// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputCognome
		lblCognome = new JLabel("Cognome");
									
		lblCognome.setBounds(75, 225, 95, 35);
		lblCognome.setVisible(false);
									
		lblCognome.setForeground(Color.WHITE);
		lblCognome.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblCognome.setFocusable(false);
									
		this.add(lblCognome);
						
						
		// Casella di testo per inserire il cognome per la registrazione
		inputCognome = new JTextField();
							
		inputCognome.setBounds(75, 255, 250, 30);
		inputCognome.setVisible(false);
							
		this.add(inputCognome);
			
// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputUsername
		lblUsername = new JLabel("Username");
			
		lblUsername.setBounds(75, 300, 95, 35);
		lblUsername.setVisible(false);
			
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblUsername.setFocusable(false);
			
		this.add(lblUsername);
		
		
		// Casella di testo per inserire lo username dell'account 
		inputUsername = new JTextField();
		
		inputUsername.setBounds(75, 330, 250, 30);
		inputUsername.setVisible(false);
		
		this.add(inputUsername);
			
// ---------------------------------------------------------------------------------------------
			
		// Label per la casella di testo inputPassword
		lblPassword = new JLabel("Password");
			
		lblPassword.setBounds(75, 375, 95, 30);
		lblPassword.setVisible(false);
			
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblPassword.setFocusable(false);
			
		this.add(lblPassword);
		
		
		// Casella di testo per inserire la password dell'account 
		inputPassword = new JPasswordField();
		
		inputPassword.setBounds(75, 405, 250, 30);
		inputPassword.setVisible(false);
		
		this.add(inputPassword);
		
// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputConfirmPassword
		lblConfermaPassword = new JLabel("Conferma password");
									
		lblConfermaPassword.setBounds(75, 450, 200, 30);
		lblConfermaPassword.setVisible(false);
									
		lblConfermaPassword.setForeground(Color.WHITE);
		lblConfermaPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblConfermaPassword.setFocusable(false);
									
		this.add(lblConfermaPassword);
		
		
		// Casella di testo per confermare la password dell'account da creare
		inputConfermaPassword = new JPasswordField();
						
		inputConfermaPassword.setBounds(75, 480, 250, 30);
		inputConfermaPassword.setVisible(false);
						
		this.add(inputConfermaPassword);
		
// ---------------------------------------------------------------------------------------------
			
		// Label che visualizza un eventuale errore nell'inserimento del nome
		erroreNome = new JLabel();
		
		erroreNome.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreNome.setForeground(new Color(255, 94, 94));
		erroreNome.setVerticalAlignment(SwingConstants.TOP);
		
		erroreNome.setVisible(false);
		erroreNome.setBounds(75, 210, 250, 20);
		
		this.add(erroreNome);
		
		
		// Label che visualizza un eventuale errore nell'inserimento del cognome
		erroreCognome = new JLabel();
				
		erroreCognome.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreCognome.setForeground(new Color(255, 94, 94));
		erroreCognome.setVerticalAlignment(SwingConstants.TOP);
				
		erroreCognome.setVisible(false);
		erroreCognome.setBounds(75, 285, 250, 20);
				
		this.add(erroreCognome);
		
		
		// Label che visualizza un eventuale errore nell'inserimento dello username
		erroreUsername = new JLabel();
		
		erroreUsername.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreUsername.setForeground(new Color(255, 94, 94));
		erroreUsername.setVerticalAlignment(SwingConstants.TOP);
		
		erroreUsername.setVisible(false);
		erroreUsername.setBounds(75, 360, 250, 20);
		
		this.add(erroreUsername);
		
		
		// Label che visualizza un eventuale errore nell'inserimento della password
		errorePassword = new JLabel();
		
		errorePassword.setFont(new Font("Arial Nova", Font.BOLD, 12));
		errorePassword.setForeground(new Color(255, 94, 94));
		errorePassword.setVerticalAlignment(SwingConstants.TOP);
		
		errorePassword.setVisible(false);
		errorePassword.setBounds(75, 435, 250, 20);
		
		this.add(errorePassword);
		
		
		// Label che visualizza un eventuale errore nella conferma della password
		erroreConfermaPassword = new JLabel();
			
		erroreConfermaPassword.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreConfermaPassword.setForeground(new Color(255, 94, 94));
		erroreConfermaPassword.setVerticalAlignment(SwingConstants.TOP);
		
		erroreConfermaPassword.setVisible(false);
		erroreConfermaPassword.setBounds(75, 510, 250, 20);
		
		this.add(erroreConfermaPassword);
		
		
		// Label che viene visualizzata se la registrazione è stata effettuata correttamente
		registrazioneEffettuata = new JLabel();
		registrazioneEffettuata.setVisible(false);
		registrazioneEffettuata.setHorizontalAlignment(SwingConstants.CENTER);
		registrazioneEffettuata.setHorizontalTextPosition(SwingConstants.CENTER);
		
		registrazioneEffettuata.setFont(new Font("Arial Nova", Font.BOLD, 20));
		registrazioneEffettuata.setForeground(new Color(0, 255, 0));
		registrazioneEffettuata.setBounds(0, 220 , 400, 50);
		
		this.add(registrazioneEffettuata);
		
// ---------------------------------------------------------------------------------------------
		
		// Pulsante per confermare le credenziali di login
		btnConfermaLogin = new JButton("Conferma");
		
		btnConfermaLogin.setBounds(100, 480, 200, 45);
		btnConfermaLogin.setVisible(false);
		btnConfermaLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnConfermaLogin);
		
		
		// Pulsante per confermare i dati della registrazione
		btnConfermaRegistr = new JButton("Conferma");
			
		btnConfermaRegistr.setBounds(100, 600, 200, 45);
		btnConfermaRegistr.setVisible(false);
		btnConfermaRegistr.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
		this.add(btnConfermaRegistr);
		
// ---------------------------------------------------------------------------------------------
		
		// Pulsante per tornare indietro
		btnBack = new JButton("Torna indietro");
			
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
				registrazioneEffettuata.setVisible(false);
			}
		});
		
		
		// Funzionalità per il pulsante btnRegistrati (rende visibile la schermata di registrazione)
		btnRegistrati.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
					
				titolo.setText("Registrazione");
				titolo.setBounds(0, 100, 400, 50);
				btnBack.setVisible(true);
					
				btnAccedi.setVisible(false);
				btnRegistrati.setVisible(false);
					
				lblNome.setVisible(true);
				inputNome.setVisible(true);
				
				lblCognome.setVisible(true);
				inputCognome.setVisible(true);
					
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
				erroreCognome.setVisible(false);
				erroreUsername.setVisible(false);
				errorePassword.setVisible(false);
				erroreConfermaPassword.setVisible(false);
						
				btnAccedi.setVisible(true);
				btnRegistrati.setVisible(true);
						
				lblNome.setVisible(false);
				inputNome.setText("");
				inputNome.setVisible(false);
				
				lblCognome.setVisible(false);
				inputCognome.setText("");
				inputCognome.setVisible(false);
						
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
				String newCognome = inputCognome.getText().trim();
				String newUsername = inputUsername.getText().trim();
				String newPassword = inputPassword.getText().trim();
				String newConfermaPassword = inputConfermaPassword.getText().trim();
					
				try {
						
					String resultNome = Registrazione.checkNome(newNome);
					String resultCognome = Registrazione.checkCognome(newCognome);
					String resultUsername = Registrazione.checkUsername(newUsername);
					String resultPassword = Registrazione.checkPassword(newPassword);
					String resultConfermaPassword = Registrazione.checkConfermaPassword(newPassword, newConfermaPassword);
					
					if (!resultNome.equals("Verificata.")) {
						erroreNome.setText(resultNome);
						erroreNome.setVisible(true);
						inputNome.setBorder(new LineBorder(Color.RED, 1));
					} else {
						erroreNome.setVisible(false);
						inputNome.setBorder(new LineBorder(Color.GREEN, 1));
					}
					
					
					if (!resultCognome.equals("Verificata.")) {
						erroreCognome.setText(resultCognome);
						erroreCognome.setVisible(true);
						inputCognome.setBorder(new LineBorder(Color.RED, 1));
					} else {
						erroreCognome.setVisible(false);
						inputCognome.setBorder(new LineBorder(Color.GREEN, 1));
					}
					
					
					if (!resultUsername.equals("Verificata.")) {
						erroreUsername.setText(resultUsername);
						erroreUsername.setVisible(true);
						inputUsername.setBorder(new LineBorder(Color.RED, 1));
					} else {
						erroreUsername.setVisible(false);
						inputUsername.setBorder(new LineBorder(Color.GREEN, 1));
					}
					
					
					if (!resultPassword.equals("Verificata.")) {
						errorePassword.setText(resultPassword);
						errorePassword.setVisible(true);
						inputPassword.setBorder(new LineBorder(Color.RED, 1));
					} else {
						errorePassword.setVisible(false);
						inputPassword.setBorder(new LineBorder(Color.GREEN, 1));
					}
					
					
					if (!resultConfermaPassword.equals("Verificata.")) {
						erroreConfermaPassword.setText(resultConfermaPassword);
						erroreConfermaPassword.setVisible(true);
						inputConfermaPassword.setBorder(new LineBorder(Color.RED, 1));
					} else {
						erroreConfermaPassword.setVisible(false);
						inputConfermaPassword.setBorder(new LineBorder(Color.GREEN, 1));
					}
					
					if (resultNome.equals("Verificata.") && resultUsername.equals("Verificata.") && resultPassword.equals("Verificata.") && resultConfermaPassword.equals("Verificata.")) {
						
						titolo.setText("");
						btnBack.setVisible(false);
						
						registrazioneEffettuata.setText(Registrazione.addUser(newNome, newCognome, newUsername, newPassword, newConfermaPassword));
						registrazioneEffettuata.setVisible(true);
								
						btnAccedi.setVisible(true);
								
						lblNome.setVisible(false);
						inputNome.setText("");
						inputNome.setVisible(false);
						
						lblCognome.setVisible(false);
						inputCognome.setText("");
						inputCognome.setVisible(false);
								
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
					}
							
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
			
				
		// Funzionalità per il pulsante "Conferma Login"
		btnConfermaLogin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String username = inputUsername.getText().trim();
				String password = inputPassword.getText().trim();
				
				try {
					
					String resoconto = utente.accedi(username, password);
					
					if (resoconto.equals("Verificata.")) {
						
						titolo.setText(utente.getUsername());
						btnBack.setVisible(false);
						
						lblUsername.setVisible(false);
						inputUsername.setText("");
						inputUsername.setVisible(false);
								
						lblPassword.setVisible(false);
						inputPassword.setText("");
						inputPassword.setVisible(false);
						
						btnConfermaLogin.setVisible(false);
						
						erroreUsername.setVisible(false);
						errorePassword.setVisible(false);
					}
					
					else {
						
						errorePassword.setVisible(true);
						inputPassword.setBorder(new LineBorder(Color.RED, 1));
						
						if (password.isBlank()) {
							errorePassword.setText("Password non inserita.");
						} else {
							if (resoconto.equals("Password errata.")) {							
								errorePassword.setText(resoconto);
							}
						}
						
						if (resoconto.equals("Utente non esistente.") || resoconto.equals("Username non inserito.")) {
							
							erroreUsername.setText(resoconto);
							erroreUsername.setVisible(true);
							inputUsername.setBorder(new LineBorder(Color.RED, 1));
						
						} else {
							
							erroreUsername.setVisible(false);
							inputUsername.setBorder(new LineBorder(Color.GREEN, 1));
						}
						
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}