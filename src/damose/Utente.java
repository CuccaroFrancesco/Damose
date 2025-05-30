package damose;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Utente {
	
	private String nome, cognome, username, password;
	private boolean isLogged;
	private List<String> lineePreferite, fermatePreferite;
	private PreferitiObserver observer;
	
	
	// Metodi get e set per il nome associato all'utente
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	// Metodi get e set per il cognome associato all'utente
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	
	// Metodi get e set per lo username associato all'utente
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	// Metodi get e set per la password associata all'utente
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	// Metodi get e set per le linee preferite dell'utente
	public List<String> getLineePreferite() {
		return lineePreferite;
	}

	public void setLineePreferite(List<String> lineePreferite) {
		this.lineePreferite = lineePreferite.equals(" ") ? new ArrayList<>() : lineePreferite;
	}
	
	
	// Metodi get e set per le fermate preferite dell'utente
	public List<String> getFermatePreferite() {
		return fermatePreferite;
	}
	
	public void setFermatePreferite(List<String> fermatePreferite) {
		this.fermatePreferite = fermatePreferite.equals(" ") ? new ArrayList<>() : fermatePreferite;
	}
	
	
	// Metodo get per ottenere lo stato (loggato o non loggato) dell'utente
	public boolean getIsLogged() {
		return this.isLogged;
	}

// ---------------------------------------------------------------------------------------------

	// Metodo set per l'observer dei preferiti dell'utente
	public void setObserver(PreferitiObserver observer) {
		this.observer = observer;
	}
		
		
	// Metodo che notifica l'observer di un determinato cambiamento nei preferiti
	private void notificaObserver() {
		if (observer != null) {
		    observer.onPreferitiChanged();
		}
	}
		
// ---------------------------------------------------------------------------------------------
	
	// Metodo per modificare le linee preferite (sia attributo che nel file di testo) dell'utente loggato
	public void cambiaPreferiti(List<String> lineePreferite, List<String> fermatePreferite) {
		
		this.setLineePreferite(lineePreferite);
		this.setFermatePreferite(fermatePreferite);

		try {
			Path path = Paths.get("files/utenti.txt");
			List<String> righe = Files.readAllLines(path);

			for (int i = 0; i < righe.size(); i++) {
				if (righe.get(i).startsWith(this.username)) {
					righe.set(i, this.username + "," + this.nome + "," + this.cognome + "," + this.password + "," + String.join("-", lineePreferite) + "," + String.join("-", fermatePreferite));
					break;
				}
			}

			Files.write(path, righe);

		} catch (IOException e) {
			e.printStackTrace();
		}

		notificaObserver();
	}

	
	// Metodo per modificare le fermate preferite (sia attributo che nel file di testo) dell'utente loggato
	public void cambiaFermatePreferite(List<String> fermatePreferite) throws Exception {
		
		this.setFermatePreferite(fermatePreferite);

		try {
			Path path = Paths.get("files/utenti.txt");
			List<String> righe = Files.readAllLines(path);

			for (int i = 0; i < righe.size(); i++) {
				if (righe.get(i).startsWith(this.username)) {
					righe.set(i, this.username + "," + this.nome + "," + this.cognome + "," + String.join("-", lineePreferite) + "," + String.join("-", this.fermatePreferite));
					break;
				}
			}

			Files.write(path, righe);

		} catch (IOException e) {
			e.printStackTrace();
		}

		notificaObserver();
	}
	
// ---------------------------------------------------------------------------------------------
	
	// Metodo che gestisce l'accesso a un utente già esistente
	public String accedi(String username, String password) throws IOException {
		
		if (username.isBlank()) {        	
        	return "Username non inserito.";
        }
		
		BufferedReader reader = new BufferedReader(new FileReader("files/utenti.txt"));
        String riga;
        
        while ((riga = reader.readLine()) != null) {

         	List<String> dati = new ArrayList<>(List.of(riga.split(",", -1)));

            if (dati.size() > 0 && dati.get(0).equals(username.trim())) {
            	
            	if(dati.get(3).trim().equals(password)) {  
            		
            		this.isLogged = true;
            		this.setUsername(username.trim());
            		this.setNome(dati.get(1).trim());
            		this.setCognome(dati.get(2).trim());
            		this.setPassword(password.trim());

					List<String> listaLineePreferite = new ArrayList<>();
					if (!dati.get(4).isBlank()) {
						for (String linea : dati.get(4).trim().split("-")) {
							if (!linea.isBlank()) listaLineePreferite.add(linea);
						}
					}

					this.setLineePreferite(listaLineePreferite);

					List<String> listaFermatePreferite = new ArrayList<>();
					if (!dati.get(5).isBlank()) {
						for (String fermata : dati.get(5).trim().split("-")) {
							if (!fermata.isBlank()) listaFermatePreferite.add(fermata);
						}
					}

					this.setFermatePreferite(listaFermatePreferite);
            		return "Verificata.";
            		
            	} else {
            		return "Password errata.";
            	}
            }
        }

		reader.close();
		
		return "Utente non esistente.";
	}
	
	
	// Metodo che gestisce l'eventuale logout di un utente
	public void logout() {
		this.nome = null;
		this.cognome = null;
		this.username = null;
		this.password = null;
		this.lineePreferite = new ArrayList<>();
		this.fermatePreferite = new ArrayList<>();
		this.isLogged = false;
	}
}