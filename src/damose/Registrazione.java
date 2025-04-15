package damose;

import java.io.*;

public class Registrazione {
	
	// Metodo che controlla la validità del nome inserito
	public String checkNome(String newName) {
		
		if (!newName.isBlank()) {
			return "Verificata.";
		} else {
			return "Nome non inserito.";
		}
	}
	
	
	// Metodo che controlla la validità dello username inserito
	public String checkUsername(String newUsername) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("files/utenti.txt"));
        String riga;
        
        if (newUsername.isBlank()) {        	
        	reader.close();
        	return "Username non inserito.";
        }
        
        while ((riga = reader.readLine()) != null) {
            String[] dati = riga.split(",");
            
            if (dati.length > 0 && dati[0].trim().equals(newUsername.trim())) {
                reader.close();
                return "Username già in uso.";
            }
        }

		reader.close();
		
        return "Verificata.";
	}
	
	
	// Metodo che controlla la validità della password inserita (lunghezza minima, maiuscole e minuscole, simboli, conferma password)
	public String checkPassword(String newPass, String confirmPass) {
		
	    boolean haMaiuscola = false;
	    boolean haMinuscola = false;
	    boolean haNumero = false;
	    boolean haSimbolo = false;
	    String simboli = "/*-+!£$%&=?€";
	    
	    if (newPass.isBlank()) {
        	return "Password non inserita.";
	    }
	    
	    if (newPass.length() < 8) {
	        return "La password deve essere lunga almeno 8 caratteri.";
	    }
	    
	    for (char c : newPass.toCharArray()) {
	        
	    	if (Character.isUpperCase(c)) {
	        	haMaiuscola = true;
	        } else if (Character.isLowerCase(c)) {
	        	haMinuscola = true;
	        } else if (Character.isDigit(c)) {
	        	haNumero = true;
	        } else if (simboli.indexOf(c) >= 0) {
	        	haSimbolo = true; 
	        }
	    }
	    
	    if (!haMaiuscola) {
	        return "La password deve contenere almeno una lettera maiuscola.";
	    }
	    
	    if (!haMinuscola) {
	        return "La password deve contenere almeno una lettera minuscola.";
	    }
	    
	    if (!haNumero) {
	        return "La password deve contenere almeno un numero.";
	    }
	    
	    if (!haSimbolo) {
	        return "La password deve contenere almeno un simbolo speciale tra i seguenti: /*-+!£$%&=?€";
	    }
	     
	    if (confirmPass.isBlank()) {
        	return "Conferma la password.";
	    }
	    
	    if (!newPass.equals(confirmPass)) {
	    	return "Le due password non corrispondono.";
	    }
	    
	    return "Verificata.";
	}

	
	// Metodo che gestisce l'aggiunta di nuovi user al file di testo che svolge il ruolo di "database"
	public String addUser(String nome, String username, String password, String confirmPass) throws IOException {
		
		// Verifica del superamento dei controlli per le credenziali dell'account
		if (!checkNome(nome).equals("Verificata.")) {
			return checkNome(nome);
		}
		
		if (!checkUsername(username).equals("Verificata.")) {
			return checkUsername(username);
		}
		
		if (!checkPassword(password, confirmPass).equals("Verificata.")) {
            return checkPassword(password, confirmPass);
		}
		
		// Inserimento dell'utente nel file di testo
		BufferedWriter writer = new BufferedWriter(new FileWriter("files/utenti.txt", true));
		writer.write(username + "," + nome + "," + password + "\n");
		writer.flush();
		writer.close();
		
		return "Utente registrato correttamente!";
	}	
}