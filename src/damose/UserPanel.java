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
		titolo.setBounds(0, 200, 400, 50);
				
		this.add(titolo);
		
		
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
			
// ---------------------------------------------------------------------------------------------
		
		// Label per la casella di testo inputUsername
		JLabel lblUsername = new JLabel("Username");
			
		lblUsername.setBounds(75, 290, 95, 35);
		lblUsername.setVisible(false);
			
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblUsername.setFocusable(false);
			
		this.add(lblUsername);
		
		
		// Casella di testo per inserire lo username dell'account 
		JTextField inputUsername = new JTextField();
		
		inputUsername.setBounds(75, 320, 250, 35);
		inputUsername.setVisible(false);
		
		this.add(inputUsername);
			
// ---------------------------------------------------------------------------------------------
			
		// Label per la casella di testo inputPassword
		JLabel lblPassword = new JLabel("Password");
			
		lblPassword.setBounds(75, 370, 95, 35);
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
		JLabel lblConfirmPassword = new JLabel("Conferma password");
									
		lblConfirmPassword.setBounds(75, 450, 200, 35);
		lblConfirmPassword.setVisible(false);
									
		lblConfirmPassword.setForeground(Color.WHITE);
		lblConfirmPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblConfirmPassword.setFocusable(false);
									
		this.add(lblConfirmPassword);
		
		
		// Casella di testo per confermare la password dell'account da creare
		JPasswordField inputConfirmPassword = new JPasswordField();
						
		inputConfirmPassword.setBounds(75, 480, 250, 35);
		inputConfirmPassword.setVisible(false);
						
		this.add(inputConfirmPassword);
			
// ---------------------------------------------------------------------------------------------
			
		// Label per la casella di testo inputNome
		JLabel lblNome = new JLabel("Nome");
					
		lblNome.setBounds(75, 210, 95, 35);
		lblNome.setVisible(false);
					
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblNome.setFocusable(false);
					
		this.add(lblNome);
		
		
		// Casella di testo per inserire il vero nome per la registrazione
		JTextField inputNome = new JTextField();
			
		inputNome.setBounds(75, 240, 250, 35);
		inputNome.setVisible(false);
			
		this.add(inputNome);
		
// ---------------------------------------------------------------------------------------------
			
		JTextPane resoconto = new JTextPane();
		
		resoconto.setMargin(new Insets(3, 5, 3, 5));
		resoconto.setBounds(75, 100, 250, 35);
		resoconto.setVisible(false);
		
		resoconto.setText("Errore");
		resoconto.setFont(new Font("Arial Nova", Font.BOLD, 12));
		resoconto.setFocusable(false);
		resoconto.setEditable(false);
		
		this.add(resoconto);
			
// ---------------------------------------------------------------------------------------------
		
		// Pulsante per confermare le credenziali di login
		JButton btnConfermaLogin = new JButton("Conferma");
		
		btnConfermaLogin.setBounds(100, 480, 200, 45);
		btnConfermaLogin.setVisible(false);
		btnConfermaLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnConfermaLogin);
		
		// Pulsante per confermare i dati della registrazione
		JButton btnConfermaRegistr = new JButton("Conferma");
			
		btnConfermaRegistr.setBounds(100, 560, 200, 45);
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
					
				lblConfirmPassword.setVisible(true);
				inputConfirmPassword.setVisible(true);
					
				btnConfermaRegistr.setVisible(true);
			}
		});
		
		
		// Funzionalità per il pulsante btnBack (torna indietro, rendendo visibile il profilo Ospite o Utente)
		btnBack.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e) {
						
				titolo.setText("Ospite");
				titolo.setBounds(0, 200, 400, 50);
				btnBack.setVisible(false);
				resoconto.setVisible(false);
						
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
						
				lblConfirmPassword.setVisible(false);
				inputConfirmPassword.setText("");
				inputConfirmPassword.setVisible(false);
						
				btnConfermaRegistr.setVisible(false);
				btnConfermaLogin.setVisible(false);
			}
		});
				
				
// ---------------------------------------------------------------------------------------------
				
				
		// Funzionalità per il pulsante "Conferma registrazione"
		btnConfermaRegistr.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
					
				String newUsername = inputUsername.getText().trim();
				String newNome = inputNome.getText().trim();
				String newPassword = inputPassword.getText().trim();
				String newConfirmPass = inputConfirmPassword.getText().trim();
					
				Registrazione registrazione = new Registrazione();
					
				try {
						
					String result = registrazione.addUser(newNome, newUsername, newPassword, newConfirmPass);
					resoconto.setText(result);
						
					if(result.endsWith("!")) {
						resoconto.setVisible(true);
						resoconto.setForeground(new Color(0, 255, 0));
					}
						
					else {
						resoconto.setVisible(true);
						resoconto.setForeground(new Color(255, 0, 0));
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