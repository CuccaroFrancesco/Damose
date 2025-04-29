package damose;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utente {
	
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String[] lineePreferite;
	private String[] fermatePreferite;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String[] getLineePreferite() {
		return lineePreferite;
	}

	public void setLineePreferite(String[] lineePreferite) {
		this.lineePreferite = lineePreferite;
	}
	
	public String[] getFermatePreferite() {
		return fermatePreferite;
	}

	public void setFermatePreferite(String[] fermatePreferite) {
		this.fermatePreferite = fermatePreferite;
	}
	
	
	// Metodo che gestisce l'accesso a un utente già esistente
	public String accedi(String username, String password) throws IOException {
		
		if (username.isBlank()) {        	
        	return "Username non inserito.";
        }
		
		BufferedReader reader = new BufferedReader(new FileReader("files/utenti.txt"));
        String riga;
        
        while ((riga = reader.readLine()) != null) {
            String[] dati = riga.split(",");
            
            if (dati.length > 0 && dati[0].trim().equals(username.trim())) {
            	
            	if(dati[3].trim().equals(password)) {  
            		
            		this.setUsername(username.trim());
            		this.setNome(dati[1].trim());
            		this.setCognome(dati[2].trim());
            		this.setPassword(password.trim());
            		this.setLineePreferite(dati[4].split("-"));
            		this.setFermatePreferite(dati[5].split("-"));
            		return "Verificata.";
            		
            	} else {
            		return "Password errata.";
            	}
            }
        }

		reader.close();
		
		return "Utente non esistente.";
	}
}
