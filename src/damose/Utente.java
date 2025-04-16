package damose;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utente {
	
	private String nome;
	private String username;
	private String password;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
            	
            	if(dati[2].trim().equals(password)) {    
            		
            		this.setUsername(username.trim());
            		this.setNome(dati[1].trim());
            		this.setPassword(password.trim());
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
