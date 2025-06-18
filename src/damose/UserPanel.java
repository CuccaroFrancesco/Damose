package damose;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;



public class UserPanel extends JPanel implements PreferitiObserver {
	
	private Frame frame;
	
	private JButton btnProfilePic, btnSettings, btnAccedi, btnRegistrati, btnConfermaLogin, btnConfermaRegistr, btnLogout, btnBack, btnToggleFermate, btnToggleLinee;
	private JLabel titolo, lblNome, lblCognome, lblUsername, lblPassword, lblConfermaPassword, lblNomeCognomeUtente,
	               erroreNome, erroreCognome, erroreUsername, errorePassword, erroreConfermaPassword;
	private JTextField inputNome, inputCognome, inputUsername;
	private JPasswordField inputPassword, inputConfermaPassword;
	private JPanel panelLineePreferite, panelFermatePreferite;
	private JScrollPane fermateScrollPane, lineeScrollPane;


	// Costruzione del pannello utente (login, registrazione e profilo)
	public UserPanel(Frame frame) {
		
		this.frame = frame;
		
		frame.getUtente().setObserver(this);
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		
		// Scritta "Ospite"
		titolo = new JLabel("Ospite");
				
		titolo.setForeground(Color.WHITE);
		titolo.setFont(new Font("Arial Nova", Font.BOLD, 24));
		titolo.setFocusable(false);
				
		titolo.setHorizontalAlignment(SwingConstants.CENTER);
		titolo.setBounds(0, 180, 350, 50);
				
		this.add(titolo);


		// JLabel destinato a visualizzare nome e cognome dell'utente loggato
		lblNomeCognomeUtente = new JLabel();

		lblNomeCognomeUtente.setForeground(new Color(210, 210, 210));
		lblNomeCognomeUtente.setFont(new Font("Arial Nova", Font.ITALIC, 14));
		lblNomeCognomeUtente.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeCognomeUtente.setBounds(100, 240, 150, 20);

		lblNomeCognomeUtente.setVisible(false);

		this.add(lblNomeCognomeUtente);


		// Pulsante per l'icona dell'utente (nessuna funzionalità, serve solo per visualizzare comodamente l'immagine)
		btnProfilePic = new JButton();

		btnProfilePic.setFocusable(false);
		btnProfilePic.setBorderPainted(false);
		btnProfilePic.setFocusPainted(false);
		btnProfilePic.setContentAreaFilled(false);

		ImageIcon iconProfilePic = new ImageIcon(getClass().getResource("/assets/user_placeholder.png"));
		Image scaledImageProfilePic = iconProfilePic.getImage().getScaledInstance(144, 144, Image.SCALE_SMOOTH);
		ImageIcon newIconProfilePic = new ImageIcon(scaledImageProfilePic);
		btnProfilePic.setIcon(newIconProfilePic);

		btnProfilePic.setBounds(103, 55, 144, 144);
		btnProfilePic.setVisible(false);

		this.add(btnProfilePic);


		// Pulsante per aprire le impostazioni
		btnSettings = new JButton();

		btnSettings.setBounds(-55, 4, 160, 30);
		btnSettings.setVisible(false);
		btnSettings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnSettings.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnSettings.setForeground(Color.WHITE);

		btnSettings.setBorderPainted(false);
		btnSettings.setFocusPainted(false);
		btnSettings.setContentAreaFilled(false);

		ImageIcon iconSettings = new ImageIcon(getClass().getResource("/assets/settings.png"));
		Image scaledImageSettings = iconSettings.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		ImageIcon newIconSettings = new ImageIcon(scaledImageSettings);
		btnSettings.setIcon(newIconSettings);

		this.add(btnSettings);
		
		
		// Pulsante per l'accesso (con account già esistente)
		btnAccedi = new JButton("Accedi");
		
		btnAccedi.setBounds(50, 300, 250, 45);
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnAccedi);
		
		
		// Pulsante per la registrazione (creazione di un nuovo account)
		btnRegistrati = new JButton("Registrati");
		
		btnRegistrati.setBounds(50, 380, 250, 45);
		btnRegistrati.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		this.add(btnRegistrati);


// ---------------------------------------------------------------------------------------------


		// Label per la casella di testo inputNome
		lblNome = new JLabel("Nome");
							
		lblNome.setBounds(50, 150, 95, 35);
		lblNome.setVisible(false);
							
		lblNome.setForeground(Color.WHITE);
		lblNome.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblNome.setFocusable(false);
							
		this.add(lblNome);
				
				
		// Casella di testo per inserire il nome per la registrazione
		inputNome = new JTextField();
					
		inputNome.setBounds(50, 180, 250, 30);
		inputNome.setVisible(false);
					
		this.add(inputNome);


// ---------------------------------------------------------------------------------------------


		// Label per la casella di testo inputCognome
		lblCognome = new JLabel("Cognome");
									
		lblCognome.setBounds(50, 225, 95, 35);
		lblCognome.setVisible(false);
									
		lblCognome.setForeground(Color.WHITE);
		lblCognome.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblCognome.setFocusable(false);
									
		this.add(lblCognome);
						
						
		// Casella di testo per inserire il cognome per la registrazione
		inputCognome = new JTextField();
							
		inputCognome.setBounds(50, 255, 250, 30);
		inputCognome.setVisible(false);
							
		this.add(inputCognome);


// ---------------------------------------------------------------------------------------------


		// Label per la casella di testo inputUsername
		lblUsername = new JLabel("Username");
			
		lblUsername.setBounds(50, 300, 95, 35);
		lblUsername.setVisible(false);
			
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblUsername.setFocusable(false);
			
		this.add(lblUsername);
		
		
		// Casella di testo per inserire lo username dell'account 
		inputUsername = new JTextField();
		
		inputUsername.setBounds(50, 330, 250, 30);
		inputUsername.setVisible(false);
		
		this.add(inputUsername);


// ---------------------------------------------------------------------------------------------


		// Label per la casella di testo inputPassword
		lblPassword = new JLabel("Password");
			
		lblPassword.setBounds(50, 375, 95, 30);
		lblPassword.setVisible(false);
			
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblPassword.setFocusable(false);
			
		this.add(lblPassword);
		
		
		// Casella di testo per inserire la password dell'account 
		inputPassword = new JPasswordField();
		
		inputPassword.setBounds(50, 405, 250, 30);
		inputPassword.setVisible(false);
		
		this.add(inputPassword);


// ---------------------------------------------------------------------------------------------


		// Label per la casella di testo inputConfirmPassword
		lblConfermaPassword = new JLabel("Conferma password");
									
		lblConfermaPassword.setBounds(50, 450, 200, 30);
		lblConfermaPassword.setVisible(false);
									
		lblConfermaPassword.setForeground(Color.WHITE);
		lblConfermaPassword.setFont(new Font("Arial Nova", Font.BOLD, 18));
		lblConfermaPassword.setFocusable(false);
									
		this.add(lblConfermaPassword);
		
		
		// Casella di testo per confermare la password dell'account da creare
		inputConfermaPassword = new JPasswordField();
						
		inputConfermaPassword.setBounds(50, 480, 250, 30);
		inputConfermaPassword.setVisible(false);
						
		this.add(inputConfermaPassword);


// ---------------------------------------------------------------------------------------------


		// Label che visualizza un eventuale errore nell'inserimento del nome
		erroreNome = new JLabel();
		
		erroreNome.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreNome.setForeground(new Color(255, 94, 94));
		erroreNome.setVerticalAlignment(SwingConstants.TOP);
		
		erroreNome.setVisible(false);
		erroreNome.setBounds(50, 210, 250, 20);
		
		this.add(erroreNome);
		
		
		// Label che visualizza un eventuale errore nell'inserimento del cognome
		erroreCognome = new JLabel();
				
		erroreCognome.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreCognome.setForeground(new Color(255, 94, 94));
		erroreCognome.setVerticalAlignment(SwingConstants.TOP);
				
		erroreCognome.setVisible(false);
		erroreCognome.setBounds(50, 285, 250, 20);
				
		this.add(erroreCognome);
		
		
		// Label che visualizza un eventuale errore nell'inserimento dello username
		erroreUsername = new JLabel();
		
		erroreUsername.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreUsername.setForeground(new Color(255, 94, 94));
		erroreUsername.setVerticalAlignment(SwingConstants.TOP);
		
		erroreUsername.setVisible(false);
		erroreUsername.setBounds(50, 360, 250, 20);
		
		this.add(erroreUsername);
		
		
		// Label che visualizza un eventuale errore nell'inserimento della password
		errorePassword = new JLabel();
		
		errorePassword.setFont(new Font("Arial Nova", Font.BOLD, 12));
		errorePassword.setForeground(new Color(255, 94, 94));
		errorePassword.setVerticalAlignment(SwingConstants.TOP);
		
		errorePassword.setVisible(false);
		errorePassword.setBounds(50, 435, 250, 20);
		
		this.add(errorePassword);
		
		
		// Label che visualizza un eventuale errore nella conferma della password
		erroreConfermaPassword = new JLabel();
			
		erroreConfermaPassword.setFont(new Font("Arial Nova", Font.BOLD, 12));
		erroreConfermaPassword.setForeground(new Color(255, 94, 94));
		erroreConfermaPassword.setVerticalAlignment(SwingConstants.TOP);
		
		erroreConfermaPassword.setVisible(false);
		erroreConfermaPassword.setBounds(50, 510, 250, 20);
		
		this.add(erroreConfermaPassword);


// ---------------------------------------------------------------------------------------------


		// Pulsante per confermare le credenziali di login
		btnConfermaLogin = new JButton("Conferma");
		
		btnConfermaLogin.setBounds(75, 480, 200, 45);
		btnConfermaLogin.setVisible(false);
		btnConfermaLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		Action pressedConfermaLogin = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				btnConfermaLogin.doClick();
			}
		};
		
		InputMap inputMapLogin = btnConfermaLogin.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMapLogin = btnConfermaLogin.getActionMap();
        
        inputMapLogin.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
        actionMapLogin.put("pressed", pressedConfermaLogin);
        
        this.add(btnConfermaLogin);
		
		
		// Pulsante per confermare i dati della registrazione
		btnConfermaRegistr = new JButton("Conferma");
			
		btnConfermaRegistr.setBounds(75, 600, 200, 45);
		btnConfermaRegistr.setVisible(false);
		btnConfermaRegistr.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		Action pressedConfermaRegistr = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				btnConfermaRegistr.doClick();
			}
		};
		
		InputMap inputMapRegistr = btnConfermaRegistr.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMapRegistr = btnConfermaRegistr.getActionMap();
        
        inputMapRegistr.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
        actionMapRegistr.put("pressed", pressedConfermaRegistr);
        
        this.add(btnConfermaRegistr);


// ---------------------------------------------------------------------------------------------


		// Pulsante per tornare indietro
		btnBack = new JButton(" Torna indietro");
			
		btnBack.setBounds(-5, 5, 160, 25);
		btnBack.setVisible(false);
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
		btnBack.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnBack.setForeground(Color.WHITE);
			
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		
		ImageIcon iconBack = new ImageIcon(getClass().getResource("/assets/indietro.png"));
        Image scaledImageBack = iconBack.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newIconBack = new ImageIcon(scaledImageBack);
        btnBack.setIcon(newIconBack);
        
		this.add(btnBack);
		
		
		// Pulsante per effettuare il logout
		btnLogout = new JButton(" Logout");
		
		btnLogout.setBounds(230, 4, 120, 30);
		btnLogout.setVisible(false);
		btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnLogout.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnLogout.setForeground(Color.WHITE);
		
		btnLogout.setBorderPainted(false);
		btnLogout.setFocusPainted(false);
		btnLogout.setContentAreaFilled(false);
		
		ImageIcon iconLogout = new ImageIcon(getClass().getResource("/assets/logout.png"));
        Image scaledImageLogout = iconLogout.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon newIconLogout = new ImageIcon(scaledImageLogout);
        btnLogout.setIcon(newIconLogout);
        
		this.add(btnLogout);


// ---------------------------------------------------------------------------------------------


		// Funzionalità per il pulsante btnAccedi (rende visibile la schermata di login)
		btnAccedi.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
				
				nascondiTutto();
				btnBack.setVisible(true);
				
				titolo.setText("Login");
				titolo.setBounds(0, 200, 350, 50);
				titolo.setVisible(true);
				
				inputUsername.setVisible(true);
				inputPassword.setVisible(true);
				lblUsername.setVisible(true);
				lblPassword.setVisible(true);
					
				btnConfermaLogin.setVisible(true);
				
				inputUsername.requestFocus();
			}
		});
		
		
		// Funzionalità per il pulsante btnRegistrati (rende visibile la schermata di registrazione)
		btnRegistrati.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
					
				nascondiTutto();
				btnBack.setVisible(true);
				
				titolo.setText("Registrazione");
				titolo.setBounds(0, 100, 350, 50);
				titolo.setVisible(true);
					
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
				nascondiTutto();	
				
				titolo.setText("Ospite");
				titolo.setBounds(0, 200, 350, 50);
				titolo.setVisible(true);
				
				btnAccedi.setVisible(true);
				btnRegistrati.setVisible(true);
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
					String resultUsername = null;

					try {
						resultUsername = Registrazione.checkUsername(newUsername);
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}

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
						
						try {
							Registrazione.addUser(newNome, newCognome, newUsername, newPassword);
						} catch (URISyntaxException e1) {
							e1.printStackTrace();
						}

						nascondiTutto();
						
						titolo.setBounds(0, 180, 350, 50);
						titolo.setText("Ospite");
						titolo.setVisible(true);

						btnAccedi.setVisible(true);
						btnRegistrati.setVisible(true);

						ImageIcon iconCheck = new ImageIcon(getClass().getResource("/assets/check-notification.png"));
						Image scaledImageCheck = iconCheck.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
						ImageIcon newIconCheck = new ImageIcon(scaledImageCheck);
						frame.getNotificationPanel().getBtnMessage().setIcon(newIconCheck);
						frame.getNotificationPanel().getBtnMessage().setText("  Utente registrato con successo!");

						frame.getNotificationPanel().attivaNotifica();
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
		        	
		        	String resoconto = frame.getUtente().accedi(username, password);
		            
		            if (resoconto.equals("Verificata.")) {
		            	
		            	nascondiTutto();
		            	
		            	UserPanel.this.aggiornaPreferiti();
		            	UserPanel.this.repaint();

						btnSettings.setVisible(true);
						btnProfilePic.setVisible(true);
		                
		                titolo.setText(frame.getUtente().getUsername());
		                titolo.setBounds(0, 200, 350, 50);
		                titolo.setVisible(true);

						lblNomeCognomeUtente.setText(frame.getUtente().getNome() + " " + frame.getUtente().getCognome());
						lblNomeCognomeUtente.setVisible(true);

		                btnLogout.setVisible(true);
		                
		                frame.getStopPanel().controllaUtente(true);
		                frame.getRoutePanel().controllaUtente(true);

						ImageIcon iconCheck = new ImageIcon(getClass().getResource("/assets/check-notification.png"));
						Image scaledImageCheck = iconCheck.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
						ImageIcon newIconCheck = new ImageIcon(scaledImageCheck);
						frame.getNotificationPanel().getBtnMessage().setIcon(newIconCheck);
						frame.getNotificationPanel().getBtnMessage().setText("  Login effettuato con successo!");

						frame.getNotificationPanel().attivaNotifica();

		            } else {
		            	
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
		        } catch (URISyntaxException e2) {
					e2.printStackTrace();
				}
		    }
		});
		
		
		// Funzionalità per il pulsante "Logout"
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.getUtente().logout();
				nascondiTutto();
				
				titolo.setText("Ospite");
				titolo.setBounds(0, 200, 350, 50);
				titolo.setVisible(true);
				
				btnAccedi.setVisible(true);
				btnRegistrati.setVisible(true);
				
				frame.getStopPanel().controllaUtente(false);
				frame.getRoutePanel().controllaUtente(false);

				ImageIcon iconCheck = new ImageIcon(getClass().getResource("/assets/check-notification.png"));
				Image scaledImageCheck = iconCheck.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
				ImageIcon newIconCheck = new ImageIcon(scaledImageCheck);
				frame.getNotificationPanel().getBtnMessage().setIcon(newIconCheck);
				frame.getNotificationPanel().getBtnMessage().setText("  Logout effettuato con successo!");

				frame.getNotificationPanel().attivaNotifica();
			}
		});
	}
	
	public void onPreferitiChanged() {
		this.aggiornaPreferiti();
	}
	
	private void aggiornaPreferiti() {
		
		if (lineeScrollPane != null) remove(lineeScrollPane);
		if (fermateScrollPane != null) remove(fermateScrollPane);
		if (btnToggleLinee != null) remove(btnToggleLinee);
		if (btnToggleFermate != null) remove(btnToggleFermate);
		
		// Recupero delle linee e delle fermate preferite dell'utente 
        List<String> lineePreferite = frame.getUtente().getLineePreferite();
        List<String> fermatePreferite = frame.getUtente().getFermatePreferite();
        
        // Creazione del pannello per le linee preferite
        panelLineePreferite = new JPanel();
        
        panelLineePreferite.setLayout(null);
        panelLineePreferite.setPreferredSize(new Dimension(350, Math.max(100, lineePreferite.size() * 60)));
        panelLineePreferite.setBackground(new Color(130, 36, 51));
        
        // Creazione del pannello per le fermate preferite
        panelFermatePreferite = new JPanel();
        
        panelFermatePreferite.setLayout(null);
        panelFermatePreferite.setPreferredSize(new Dimension(350, Math.max(100, fermatePreferite.size() * 60)));
        panelFermatePreferite.setBackground(new Color(130, 36, 51));
        
        if (!lineePreferite.toString().equals("[]")) {

            for (int i = 0; i < lineePreferite.size(); i++) {

                int y = i * 60;

				// Ottenimento della linea da lineePreferite
            	String routeId = lineePreferite.get(i);

                final Route[] lineaArray = new Route[1];      // Array finale per linea
                lineaArray[0] = frame.getDati().cercaLineaByID(routeId);


				// JButton associato alla linea
                JButton lineaBtn = new JButton();

                lineaBtn.setBackground(new Color(130, 36, 51));
                lineaBtn.setBorder(BorderFactory.createRaisedBevelBorder());
				lineaBtn.setContentAreaFilled(false);
				lineaBtn.setFocusPainted(false);
                lineaBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                lineaBtn.setBounds(25, y, 50, 50);


				// Switch per la scelta dell'icona del lineaBtn in base al tipo di linea
				switch (lineaArray[0].getType()) {
					case 0:
						ImageIcon iconTram = new ImageIcon(getClass().getResource("/assets/tram.png"));
						Image scaledImageTram = iconTram.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
						ImageIcon newIconTram = new ImageIcon(scaledImageTram);
						lineaBtn.setIcon(newIconTram);

						break;

					case 1:
						switch (lineaArray[0].getShortName()) {
							case "MEA":
								ImageIcon iconMetroA = new ImageIcon(getClass().getResource("/assets/metro-a-logo-withborder.png"));
								Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
								ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
								lineaBtn.setIcon(newIconMetroA);

								break;

							case "MEB", "MEB1":
								ImageIcon iconMetroB = new ImageIcon(getClass().getResource("/assets/metro-b-logo-withborder.png"));
								Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
								ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
								lineaBtn.setIcon(newIconMetroB);

								break;

							case "MEC":
								ImageIcon iconMetroC = new ImageIcon(getClass().getResource("/assets/metro-c-logo-withborder.png"));
								Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
								ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
								lineaBtn.setIcon(newIconMetroC);

								break;
						}

						break;

					case 2:
						ImageIcon iconTreno = new ImageIcon(getClass().getResource("/assets/train.png"));
						Image scaledImageTreno = iconTreno.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
						ImageIcon newIconTreno = new ImageIcon(scaledImageTreno);
						lineaBtn.setIcon(newIconTreno);

						break;

					case 3:
						ImageIcon iconBus = new ImageIcon(getClass().getResource("/assets/bus.png"));
						Image scaledImageBus = iconBus.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
						ImageIcon newIconBus = new ImageIcon(scaledImageBus);
						lineaBtn.setIcon(newIconBus);

						break;
				}


				// Rimozione di eventuali actionListener precedenti del lineaBtn (necessario per evitare overlap)
                for (ActionListener a : lineaBtn.getActionListeners()) { lineaBtn.removeActionListener(a); }


				// Funzionalità per il lineaBtn, che rimanderà al routePanel associato a tale linea
                lineaBtn.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {

                		if (lineaArray[0] != null) {

                			frame.getRoutePanel().creaPannelloLinea(lineaArray[0]);
							frame.getRoutePanel().controllaUtente(frame.getUtente().getIsLogged());
                            LineaPainter.costruisciLineaDaDisegnare(frame.getDati().getViaggiDaVisualizzare(lineaArray[0]).get(frame.getRoutePanel().getIndiceViaggioVisualizzato()), frame.getMappa(), frame.getDati());

                        } else { System.err.println("Linea non trovata"); }
                    }
                });


                // JLabel che ospiterà le informazioni principali della linea
                JLabel nomeLinea = new JLabel();

				if (lineaArray[0] != null) nomeLinea.setText("<html><font size='5'><b>" + lineaArray[0].getShortName() + "</b></font><br><font size='3'><b>" + lineaArray[0].getAgency().getName() + "</b></font></html>");
				else nomeLinea.setText("Dati non disponibili.");
                
                nomeLinea.setFont(new Font("Arial Nova", Font.PLAIN, 16));
                nomeLinea.setForeground(Color.WHITE);
                nomeLinea.setBounds(85, y + 7, 250, 35);


				// Aggiunta delle varie componenti al panelLineePreferite
                panelLineePreferite.add(lineaBtn);
                panelLineePreferite.add(nomeLinea);
            }
            
        } else {

			// JLabel che indica l'assenza di linee preferite
        	JLabel lineeVuote = new JLabel("Nessuna linea preferita.");
        	
        	lineeVuote.setFont(new Font("Arial Nova", Font.BOLD, 16));
        	lineeVuote.setForeground(Color.WHITE);
        	lineeVuote.setBounds(25, 0, 250, 20);
        	
        	panelLineePreferite.setPreferredSize(new Dimension(350, 100));
        	panelLineePreferite.add(lineeVuote);
        }

        if (!fermatePreferite.toString().equals("[]")) {

	        for (int i = 0; i < fermatePreferite.size(); i++) {
	        	
	            int y = i * 60;

				// Ottenimento della fermata da fermatePreferite
	        	String stopId = fermatePreferite.get(i);

	            final Stop[] fermata = new Stop[1];     // Array finale per fermata
	            fermata[0] = frame.getDati().cercaFermataByID(stopId);


				// JButton associato alla fermata
	            JButton stopBtn = new JButton();

				stopBtn.setBackground(new Color(130, 36, 51));
				stopBtn.setBorder(BorderFactory.createRaisedBevelBorder());
				stopBtn.setContentAreaFilled(false);
				stopBtn.setFocusPainted(false);
				stopBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	            stopBtn.setBounds(25, y, 50, 50);

				ImageIcon iconFermata = new ImageIcon(getClass().getResource("/assets/fermata-bianco.png"));
				Image scaledImageFermata = iconFermata.getImage().getScaledInstance(27, 36, Image.SCALE_SMOOTH);
				ImageIcon newIconFermata = new ImageIcon(scaledImageFermata);
				stopBtn.setIcon(newIconFermata);


				// Rimozione di eventuali actionListener precedenti dello stopBtn (necessario per evitare overlap)
				for (ActionListener a : stopBtn.getActionListeners()) { stopBtn.removeActionListener(a); }


				// Funzionalità per lo stopBtn, che rimanderà allo stopPanel associato a tale fermata
	            stopBtn.addActionListener(new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {

	            		if (fermata[0] != null) {

	            			frame.getStopPanel().creaPannelloFermata(fermata[0]);
							frame.getStopPanel().controllaUtente(frame.getUtente().getIsLogged());
	            			frame.getMappa().centraMappa(fermata[0].getLon(), fermata[0].getLat(), 2);

	                    } else { System.err.println("Fermata non trovata"); }
	                }
	            });


				// JLabel che ospiterà le informazioni principali della fermata
	            JLabel nomeFermata = new JLabel();

				if (fermata[0] != null) nomeFermata.setText("<html><font size='5'><b>" + fermata[0].getName() + "</b></font><br><font size='3'>ID: <b>" + fermata[0].getId().getId() + "</b></font></html>");
				else nomeFermata.setText("Dati non disponibili.");
	             
	            nomeFermata.setFont(new Font("Arial Nova", Font.PLAIN, 16));
	            nomeFermata.setForeground(Color.WHITE);
	            nomeFermata.setBounds(85, y + 7, 250, 35);


				// Aggiunta delle varie componenti al panelFermatePreferite
	            panelFermatePreferite.add(stopBtn);
	            panelFermatePreferite.add(nomeFermata);
	        }
	        
	    } else {

			// JLabel che indica l'assenza di fermate preferite
	    	JLabel fermateVuote = new JLabel("Nessuna fermata preferita.");
	    	
	    	fermateVuote.setFont(new Font("Arial Nova", Font.BOLD, 16));
	    	fermateVuote.setForeground(Color.WHITE);
	    	fermateVuote.setBounds(25, 0, 250, 20);
	    	
	    	panelFermatePreferite.setPreferredSize(new Dimension(350, 100));
	    	panelFermatePreferite.add(fermateVuote);
	    }


	    // JScrollPane che contiene le linee preferite dell'utente
	    lineeScrollPane = new JScrollPane(panelLineePreferite);
	    
	    lineeScrollPane.setBorder(null);
	    lineeScrollPane.setVisible(false);
	    
	    if (lineePreferite.toString().equals("[]")) lineeScrollPane.setBounds(0, 330, 350, 40);
		else if (lineePreferite.size() < 4) lineeScrollPane.setBounds(0, 330, 350, lineePreferite.size() * 60 + 10);
	    else lineeScrollPane.setBounds(0, 330, 350, 250);
	    	
	    lineeScrollPane.getVerticalScrollBar().setUnitIncrement(12);
	    lineeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    lineeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    UserPanel.this.add(lineeScrollPane);


	    // JScrollPane che contiene le fermate preferite dell'utente
	    fermateScrollPane = new JScrollPane(panelFermatePreferite);
	    
	    fermateScrollPane.setBorder(null);
	    fermateScrollPane.setVisible(false);
	    fermateScrollPane.setBounds(0, 380, 350, 250);
	    
	    fermateScrollPane.getVerticalScrollBar().setUnitIncrement(12);
	    fermateScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    fermateScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    UserPanel.this.add(fermateScrollPane);


	    // Bottoni toggle per mostrare/nascondere i pannelli
	    btnToggleLinee = new JButton("Linee preferite: ▲");
	    
	    btnToggleLinee.setFocusPainted(false);
	    btnToggleLinee.setBackground(Color.WHITE);
	    btnToggleLinee.setBounds(25, 280, 300, 30);
	
	    btnToggleFermate = new JButton("Fermate preferite: ▲");
	    
	    btnToggleFermate.setFocusPainted(false);
	    btnToggleFermate.setBackground(Color.WHITE);
	    btnToggleFermate.setBounds(25, 330, 300, 30);
	
	    UserPanel.this.add(btnToggleLinee);
	    UserPanel.this.add(btnToggleFermate);


	    // Variabili di stato
	    final boolean[] mostraLinee = {false};
	    final boolean[] mostraFermate = {false};

	    btnToggleLinee.addActionListener(e -> {
	    	mostraLinee[0] = !mostraLinee[0];
	        lineeScrollPane.setVisible(mostraLinee[0]);
	        btnToggleLinee.setText("Linee preferite: " + (mostraLinee[0] ? "▼" : "▲"));
	            
	            if (mostraLinee[0]) {

	            	if (lineePreferite.toString().equals("[]")) {
	            		fermateScrollPane.setBounds(0, 420, 350, 250);
	                	btnToggleFermate.setBounds(25, 370, 300, 30);
	            	} else if (lineePreferite.size() < 4){
	            		fermateScrollPane.setBounds(0, 380 + lineePreferite.size() * 60 + 10, 350, 250);
	                	btnToggleFermate.setBounds(25, 330 + lineePreferite.size() * 60 + 10, 300, 30);
	            	} else {
						fermateScrollPane.setBounds(0, 660, 350, 250);
						btnToggleFermate.setBounds(25, 610, 300, 30);
					}
	            	
	            } else {
	            	fermateScrollPane.setBounds(0, 380, 350, 250);
	            	btnToggleFermate.setBounds(25, 330, 300, 30);
	            }
	            
	            UserPanel.this.repaint();
	    });

        btnToggleFermate.addActionListener(e -> {
            mostraFermate[0] = !mostraFermate[0];
            fermateScrollPane.setVisible(mostraFermate[0]);
            btnToggleFermate.setText("Fermate preferite: " + (mostraFermate[0] ? "▼" : "▲"));
            
            UserPanel.this.repaint();
        });
        
        this.repaint();
	}
	
	
	// Metodo utilizzato per nascondere tutti i componenti del pannello al momento di eventuali variazioni
	private void nascondiTutto() {
			
		inputNome.setText("");
		inputCognome.setText("");
		inputUsername.setText("");
		inputPassword.setText("");
		inputConfermaPassword.setText("");
			
		inputNome.setBorder(UIManager.getBorder("TextField.border"));
		inputCognome.setBorder(UIManager.getBorder("TextField.border"));
		inputUsername.setBorder(UIManager.getBorder("TextField.border"));
		inputPassword.setBorder(UIManager.getBorder("TextField.border"));
		inputConfermaPassword.setBorder(UIManager.getBorder("TextField.border"));
		    
		for (Component c : this.getComponents()) {
		    c.setVisible(false);
		}
	}	
}