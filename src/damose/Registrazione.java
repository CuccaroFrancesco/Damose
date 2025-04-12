package damose;

import java.io.*;
public class Registrazione {
	
	private String nome;
	private String username;
	private String password;
	
	public Boolean setNome(String newName) {
		// Controllo che il nome sia stato inserito correttamente
		if(!newName.trim().isEmpty()) {
			this.setNome(newName);
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean setUsername(String newUsername) throws IOException {
		
		// Creo il reader per il file di testo "utenti.txt"
		BufferedReader reader = new BufferedReader(new FileReader("files/utenti.txt"));
        String riga;
        
        // Controllo se l'utente esiste già
		while ((riga = reader.readLine()) != null) {
            String[] dati = riga.split(",");
            if (dati.length > 0 && dati[0]==newUsername) {
                reader.close();
                return false;
            }
        }

		// Restituisco true se l'operazione è andata a buon fine, se no false
        reader.close();
        this.username = newUsername;
        return true;
	}
	
	public String setPassword(String newPass) {
		
	    // Variabili di verifica
	    boolean haMaiuscola = false;
	    boolean haMinuscola = false;
	    boolean haNumero = false;
	    boolean haSimbolo = false;
	    
	    String simboli = "/*-+!£$%&=?€";
	    
	    // Controllo se la lunghezza è minima
	    if (newPass.length() < 8) {
	        return "La password deve essere lunga almeno 8 caratteri.";
	    }
	    
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
	        return "La password deve contenere almeno un simbolo speciale (es. !£$%&=?€).";
	    
	    
	    // Se non ci sono errori, la password è valida
	    this.password = newPass;
	    return "Verificata";
	}

	
	public String Registrazione(String nome, String username, String password) throws IOException
	{
		// Verifico che tutto abbia superato i controlli
		
		if(!setNome(nome))
			return "Nome non inserito";
		
		if (!setUsername(username))
			return "Username già in uso";
		
		if (setPassword(password) != "Verificata") 
            return setPassword(password);
		
		
		// Inserisco l'utente all'interno del file di testo
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("files/utenti.txt", true));
		writer.write(nome + "," + username + "," + password + "\n");
		writer.flush();
		writer.close();
		
		return "Utente registrato correttamente";
	}	
	
}
