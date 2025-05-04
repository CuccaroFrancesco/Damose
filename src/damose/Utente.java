/**********************************************************************************

Classe "Utente" per l'oggetto destinato a contenere l'utente eventualmente loggato
nell'applicazione durante il suo utilizzo.

METODI:
- getNome(), restituisce il nome associato all'utente;
- getCognome(), restituisce il cognome associato all'utente;
- getUsername(), restituisce lo username associato all'utente;
- getPassword(), restituisce la password associata all'utente;
- getLineePreferite(), restituisce le linee preferite dell'utente;
- getFermatePreferite(), restituisce le fermate preferite dell'utente;

- setNome(), setta il nome associato all'utente;
- setCognome(), setta il cognome associato all'utente;
- setUsername(), setta lo username associato all'utente;
- setPassword(), setta la password associata all'utente;
- setLineePreferite(), setta le linee preferite dell'utente;
- setFermatePreferite(), setta le fermate preferite dell'utente;

- accedi(), gestisce i vari controlli necessari a confermare l'accesso a un utente.

**********************************************************************************/

package damose;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;



public class Utente {
	
	private String nome, cognome, username, password;
	private String[] lineePreferite, fermatePreferite;
	
	
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
	public String[] getLineePreferite() {
		return lineePreferite;
	}

	public void setLineePreferite(String[] lineePreferite) {
		this.lineePreferite = lineePreferite;
	}
	
	public void cambiaLineePreferite(String[] lineePreferite) throws IOException {
		this.setLineePreferite(lineePreferite);
		File inputFile = new File("files/utenti.txt");
		File tempFile = new File("files/utenti_temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String line;
		while ((line = reader.readLine()) != null) {
		    String[] dati = line.split(",");

		        String usernameInFile = dati[0];
		        if (usernameInFile.equals(this.username)) {
		            String nuoveLinee = String.join("-", lineePreferite); 
		            System.out.println("Linee preferite: " + nuoveLinee);
		            String nuovaRiga = this.username + "," + this.nome + "," + this.cognome + "," + this.password + "," + nuoveLinee + "," + this.fermatePreferite;
		            writer.write(nuovaRiga);
		        } else {
		            writer.write(line);
		        }
		        writer.newLine();
		    }

		reader.close();
		writer.close();
		    
		if (!inputFile.delete()) {
			throw new IOException("Impossibile eliminare il file originale.");
		}
		if (!tempFile.renameTo(inputFile)) {
			throw new IOException("Impossibile rinominare il file temporaneo.");
		}
	}
	
	
	// Metodi get e set per le fermate preferite dell'utente
	public String[] getFermatePreferite() {
		return fermatePreferite;
	}
	
	public void setFermatePreferite(String[] fermatePreferite) {
		this.fermatePreferite = fermatePreferite;
	}

	public void cambiaFermatePreferite(String[] fermatePreferite) throws IOException {
		this.setFermatePreferite(fermatePreferite);
		File inputFile = new File("files/utenti.txt");
		File tempFile = new File("files/utenti_temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

		String line;
		while ((line = reader.readLine()) != null) {
		    String[] dati = line.split(",");

		        String usernameInFile = dati[0];
		        if (usernameInFile.equals(this.username)) {
		            String nuoveFermate = String.join("-", this.fermatePreferite); 
		            String nuovaRiga = this.username + "," + this.nome + "," + this.cognome + "," + this.password + "," + this.lineePreferite + "," + nuoveFermate;
		            writer.write(nuovaRiga);
		        } else {
		            writer.write(line);
		        }
		        writer.newLine();
		    }

		reader.close();
		writer.close();
		    
		if (!inputFile.delete()) {
			throw new IOException("Impossibile eliminare il file originale.");
		}
		if (!tempFile.renameTo(inputFile)) {
			throw new IOException("Impossibile rinominare il file temporaneo.");
		}
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