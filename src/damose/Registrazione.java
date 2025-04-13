package damose;

import java.io.*;
public class Registrazione {
	
	public String setNome(String newName) {
		// Controllo che il nome sia stato inserito correttamente
		if(!newName.isBlank()) {
			return "Verificata";
		} else {
			return "Nome non inserito.";
		}
	}
	
	public String setUsername(String newUsername) throws IOException {
		
		// Creo il reader per il file di testo "utenti.txt"
		BufferedReader reader = new BufferedReader(new FileReader("files/utenti.txt"));
        String riga;
        
        // Controllo che lo username sia stato inserito correttamente
        if (newUsername.isBlank()) {        	
        	reader.close();
        	return "Username non inserito.";
        }
        
        // Controllo se l'utente esiste già
		while ((riga = reader.readLine()) != null) {
            String[] dati = riga.split(",");
            if (dati.length > 0 && dati[0].trim().equals(newUsername.trim())) {
                reader.close();
                return "Username già in uso.";
            }
        }

		// Restituisco true se l'operazione è andata a buon fine, se no false
        reader.close();
        return "Verificata";
	}
	
	public String setPassword(String newPass, String confirmPass) {
		
	    // Variabili di verifica
	    boolean haMaiuscola = false;
	    boolean haMinuscola = false;
	    boolean haNumero = false;
	    boolean haSimbolo = false;
	    
	    String simboli = "/*-+!£$%&=?€";
	    
	    // Controllo che la password sia stata inserita correttamente
	    if (newPass.isBlank()) 
        	return "Password non inserita.";
	    
	    // Controllo se la lunghezza è minima
	    if (newPass.length() < 8) 
	        return "La password deve essere lunga almeno 8 caratteri.";
	    
	    
	    // Controllo ogni singolo carattere
	    for (char c : newPass.toCharArray()) {
	        if (Character.isUpperCase(c)) haMaiuscola = true;
	        else if (Character.isLowerCase(c)) haMinuscola = true;
	        else if (Character.isDigit(c)) haNumero = true;
	        else if (simboli.indexOf(c) >= 0) haSimbolo = true; 
	    }
	    
	    // Restituisco l'eventuale errore
	    if (!haMaiuscola) 
	        return "La password deve contenere almeno una lettera maiuscola.";
	    
	    if (!haMinuscola) 
	        return "La password deve contenere almeno una lettera minuscola.";
	    
	    if (!haNumero) 
	        return "La password deve contenere almeno un numero.";
	    
	    if (!haSimbolo) 
	        return "La password deve contenere almeno un simbolo speciale (/*-+!£$%&=?€).";
	    
	    // Controllo che la password sia stata confermata 
	    if (confirmPass.isBlank()) 
        	return "Conferma la password.";
        
	    
	    // Controllo che le due password siano uguali
	    if (!newPass.equals(confirmPass))
	    	return "Le due password non corrispondono.";
	    
	    
	    return "Verificata";
	}

	
	public String addUser(String nome, String username, String password, String confirmPass) throws IOException
	{
		// Verifico che tutto abbia superato i controlli
		
		if(!setNome(nome).equals("Verificata"))
			return setNome(nome);
		
		if (!setUsername(username).equals("Verificata"))
			return setUsername(username);
		
		if (!setPassword(password, confirmPass).equals("Verificata")) 
            return setPassword(password, confirmPass);
		
		
		// Inserisco l'utente all'interno del file di testo
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("files/utenti.txt", true));
		writer.write(username + "," + nome + "," + password + "\n");
		writer.flush();
		writer.close();
		
		return "Utente registrato correttamente!";
	}	
	
}
