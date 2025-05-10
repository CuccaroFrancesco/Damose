package damose;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.jxmapviewer.viewer.*;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class UserPanel extends JPanel implements PreferitiObserver {
	
	private Utente utente;
	private DatiGTFS dati;
	private Navbar navbar;
	private Mappa mappa;
	private StopPanel stopPanel;
	private RoutePanel lineaPanel;
	private JButton btnAccedi, btnRegistrati, btnConfermaLogin, btnConfermaRegistr, btnBack, btnToggleFermate, btnToggleLinee;
	private JLabel titolo, lblNome, lblCognome, lblUsername, lblPassword, lblConfermaPassword,
	               erroreNome, erroreCognome, erroreUsername, errorePassword, erroreConfermaPassword, registrazioneEffettuata;
	private JTextField inputNome, inputCognome, inputUsername;
	private JPasswordField inputPassword, inputConfermaPassword;
	private JPanel panelLineePreferite, panelFermatePreferite;
	private JScrollPane fermateScrollPane, lineeScrollPane;


	
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
	
	
	// Costruzione del pannello utente (login, registrazione e profilo)
	public UserPanel(Utente utente, DatiGTFS dati, Navbar navbar, Mappa mappa, StopPanel stopPanel, RoutePanel lineaPanel) {
		
		this.dati = dati;
		this.utente = utente;
		this.navbar = navbar;
		this.mappa = mappa;
		this.stopPanel = stopPanel;
		this.lineaPanel = lineaPanel;
		
		utente.setObserver(this);
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		
		// Scritta "Ospite"
		titolo = new JLabel("Ospite");
				
		titolo.setForeground(new Color(255, 255, 255));
		titolo.setFont(new Font("Arial Nova", Font.BOLD, 24));
		titolo.setFocusable(false);
				
		titolo.setHorizontalAlignment(SwingConstants.CENTER);
		titolo.setBounds(0, 180, 350, 50);
				
		this.add(titolo);
		
		
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
		
		
		// Label che viene visualizzata se la registrazione è stata effettuata correttamente
		registrazioneEffettuata = new JLabel();
		registrazioneEffettuata.setVisible(false);
		registrazioneEffettuata.setHorizontalAlignment(SwingConstants.CENTER);
		registrazioneEffettuata.setHorizontalTextPosition(SwingConstants.CENTER);
		
		registrazioneEffettuata.setFont(new Font("Arial Nova", Font.BOLD, 20));
		registrazioneEffettuata.setForeground(new Color(0, 255, 0));
		registrazioneEffettuata.setBounds(0, 220, 350, 50);
		
		this.add(registrazioneEffettuata);
		
// ---------------------------------------------------------------------------------------------
		
		// Pulsante per confermare le credenziali di login
		btnConfermaLogin = new JButton("Conferma");
		
		btnConfermaLogin.setBounds(75, 480, 200, 45);
		btnConfermaLogin.setVisible(false);
		btnConfermaLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		this.add(btnConfermaLogin);
		
		
		// Pulsante per confermare i dati della registrazione
		btnConfermaRegistr = new JButton("Conferma");
			
		btnConfermaRegistr.setBounds(75, 600, 200, 45);
		btnConfermaRegistr.setVisible(false);
		btnConfermaRegistr.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
		this.add(btnConfermaRegistr);
		
// ---------------------------------------------------------------------------------------------
		
		// Pulsante per tornare indietro
		btnBack = new JButton(" Torna indietro");
			
		btnBack.setBounds(0, 10, 160, 25);
		btnBack.setVisible(false);
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
		btnBack.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnBack.setForeground(new Color(255, 255, 255));
			
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setContentAreaFilled(false);
		
		ImageIcon iconBack = new ImageIcon("src/resources/indietro.png");
        Image scaledImageBack = iconBack.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newIconBack = new ImageIcon(scaledImageBack);
        btnBack.setIcon(newIconBack);
        
		this.add(btnBack);
		
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
						
						nascondiTutto();
						
						registrazioneEffettuata.setText(Registrazione.addUser(newNome, newCognome, newUsername, newPassword, newConfermaPassword));
						registrazioneEffettuata.setVisible(true);
								
						btnAccedi.setVisible(true);
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
		            	
		            	nascondiTutto();
		            	
		            	UserPanel.this.aggiornaPreferiti();
		            	UserPanel.this.repaint();
		                
		                titolo.setText(utente.getUsername());
		                titolo.setBounds(0, 120, 350, 50);
		                titolo.setVisible(true);        

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
		        }
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
        List<String> lineePreferite = utente.getLineePreferite();
        List<String> fermatePreferite = utente.getFermatePreferite();
        
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
            	
            	String routeId = lineePreferite.get(i);
                int y = i * 60;

                JButton lineaBtn = new JButton(routeId);
                
                lineaBtn.setBounds(25, y, 50, 50);
                lineaBtn.setFont(new Font("Arial Nova", Font.BOLD, 14));
                lineaBtn.setBackground(Color.WHITE); 
                lineaBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                lineaBtn.setBorder(BorderFactory.createEmptyBorder());

                final Route[] lineaArray = new Route[1];    // Array finale per linea
                lineaArray[0] = dati.cercaLineaByID(routeId);
                
                for (ActionListener a : lineaBtn.getActionListeners()) {
                	lineaBtn.removeActionListener(a);
        		}

                lineaBtn.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		if (lineaArray[0] != null) {
                			lineaPanel.creaPannelloLinea(lineaArray[0], dati);
                            LineaPainter.costruisciLineaDaDisegnare(lineaArray[0], mappa, dati);
                            stopPanel.setVisible(false);
                        } else {
                            System.out.println("Linea non trovata");
                        }
                    }
                });

                // Nome della linea o messaggio di errore
                JLabel nomeLinea = new JLabel(lineaArray[0] != null ? lineaArray[0].getAgency().getName() + " - " + lineaArray[0].getShortName() : "Dati non disponibili");
                
                nomeLinea.setFont(new Font("Arial Nova", Font.PLAIN, 16));
                nomeLinea.setForeground(Color.WHITE);
                nomeLinea.setBounds(85, y + 15, 250, 20);

                panelLineePreferite.add(lineaBtn);
                panelLineePreferite.add(nomeLinea);
            }
            
        } else {
        	
        	JLabel lineeVuote = new JLabel("Nessuna linea preferita.");
        	
        	lineeVuote.setFont(new Font("Arial Nova", Font.PLAIN, 16));
        	lineeVuote.setForeground(Color.WHITE);
        	lineeVuote.setBounds(25, 0, 250, 20);
        	
        	panelLineePreferite.setPreferredSize(new Dimension(350, 100));
        	panelLineePreferite.add(lineeVuote);
        }

        if (!fermatePreferite.toString().equals("[]")) {

	        for (int i = 0; i < fermatePreferite.size(); i++) {
	        	
	        	String stopId = fermatePreferite.get(i);
	            int y = i * 60;
	
	            JButton stopBtn = new JButton(stopId);
	             
	            stopBtn.setBounds(25, y, 50, 50);
	            stopBtn.setFocusable(false);
	            stopBtn.setFont(new Font("Arial Nova", Font.BOLD, 14));
	            stopBtn.setBackground(Color.WHITE); 
	            stopBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	            stopBtn.setBorder(BorderFactory.createEmptyBorder());
	
	            final Stop[] fermata = new Stop[1]; // Usa un array per rendere la variabile finale
	            fermata[0] = dati.cercaFermataByID(stopId);
	
	            stopBtn.addActionListener(new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		if (fermata[0] != null) {
	            			stopPanel.creaPannelloFermata(fermata[0]);
	                        mappa.centraMappa(fermata[0].getLon(), fermata[0].getLat(), 2);
	                        lineaPanel.setVisible(false);
	                    } else {
	                        System.out.println("Fermata non trovata");
	                    }
	                }
	            });
	
	            JLabel nomeFermata = new JLabel(fermata[0] != null ? fermata[0].getName() : "Dati non disponibili");
	             
	            nomeFermata.setFont(new Font("Arial Nova", Font.PLAIN, 16));
	            nomeFermata.setForeground(Color.WHITE);
	            nomeFermata.setBounds(85, y + 15, 250, 20);
	
	            panelFermatePreferite.add(stopBtn);
	            panelFermatePreferite.add(nomeFermata);
	        }
	        
	    } else {
	    	
	    	JLabel fermateVuote = new JLabel("Nessuna fermata preferita.");
	    	
	    	fermateVuote.setFont(new Font("Arial Nova", Font.PLAIN, 16));
	    	fermateVuote.setForeground(Color.WHITE);
	    	fermateVuote.setBounds(25, 0, 250, 20);
	    	
	    	panelFermatePreferite.setPreferredSize(new Dimension(350, 100));
	    	panelFermatePreferite.add(fermateVuote);
	    }

	    // JScrollPane che contiene le linee preferite dell'utente
	    lineeScrollPane = new JScrollPane(panelLineePreferite);
	    
	    lineeScrollPane.setBorder(null);
	    lineeScrollPane.setVisible(false);
	    
	    if (lineePreferite.toString().equals("[]")) lineeScrollPane.setBounds(0, 250, 350, 50);
	    else lineeScrollPane.setBounds(0, 250, 350, 250);
	    	
	    lineeScrollPane.getVerticalScrollBar().setUnitIncrement(12);
	    lineeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    lineeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    UserPanel.this.add(lineeScrollPane);
	
	    // JScrollPane che contiene le fermate preferite dell'utente
	    fermateScrollPane = new JScrollPane(panelFermatePreferite);
	    
	    fermateScrollPane.setBorder(null);
	    fermateScrollPane.setVisible(false);
	    fermateScrollPane.setBounds(0, 300, 350, 250);
	    
	    fermateScrollPane.getVerticalScrollBar().setUnitIncrement(12);
	    fermateScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    fermateScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    
	    UserPanel.this.add(fermateScrollPane);
	
	    // Bottoni toggle per mostrare/nascondere i pannelli
	    btnToggleLinee = new JButton("Linee preferite: ▲");
	    
	    btnToggleLinee.setFocusPainted(false);
	    btnToggleLinee.setBackground(Color.WHITE);
	    btnToggleLinee.setBounds(25, 200, 300, 30);
	
	    btnToggleFermate = new JButton("Fermate preferite: ▲");
	    
	    btnToggleFermate.setFocusPainted(false);
	    btnToggleFermate.setBackground(Color.WHITE);
	    btnToggleFermate.setBounds(25, 250, 300, 30);
	
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
	            	if(lineePreferite.toString().equals("[]")) {
	            		fermateScrollPane.setBounds(0, 340, 350, 250);
	                	btnToggleFermate.setBounds(25, 290, 300, 30);
	            	} else {
	            		fermateScrollPane.setBounds(0, 580, 350, 250);
	                	btnToggleFermate.setBounds(25, 530, 300, 30);
	            	}
	            	
	            } else {
	            	fermateScrollPane.setBounds(0, 300, 350, 250);
	            	btnToggleFermate.setBounds(25, 250, 300, 30);
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
}