package damose;

import java.io.*;



public class Registrazione {
	
	// Metodo che controlla la validità del nome inserito
	public static String checkNome(String newName) {
		if (!newName.isBlank()) return "Verificata.";
		else return "Nome non inserito.";
	}
	
	
	// Metodo che controlla la validità del cognome inserito
	public static String checkCognome(String newSurname) {	
		if (!newSurname.isBlank()) return "Verificata.";
		else return "Cognome non inserito.";
	}
	
	
	// Metodo che controlla la validità dello username inserito
	public static String checkUsername(String newUsername) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("src/resources/files/utenti.txt"));
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
	public static String checkPassword(String newPass) {
		
	    boolean haMaiuscola = false;
	    boolean haMinuscola = false;
	    boolean haNumero = false;
	    boolean haSimbolo = false;
	    String simboli = "/*-+!£$%&=?€";
	    
	    if (newPass.isBlank()) return "Password non inserita.";
	    if (newPass.length() < 8) return "Lunghezza minima: 8 caratteri.";
	    
	    for (char c : newPass.toCharArray()) {
	        
	    	if (Character.isUpperCase(c)) haMaiuscola = true;
	        else if (Character.isLowerCase(c)) haMinuscola = true;
	        else if (Character.isDigit(c)) haNumero = true;
	        else if (simboli.indexOf(c) >= 0) haSimbolo = true;
	    }
	    
	    if (!haMaiuscola) return "Inserire almeno una lettera maiuscola.";
	    if (!haMinuscola) return "Inserire almeno una lettera minuscola.";
	    if (!haNumero) return "Inserire almeno un numero.";
	    if (!haSimbolo) return "Inserire almeno un simbolo: /*-+!£$%&=?€";
	    
	    return "Verificata.";
	}
	
	
	// Metodo che controlla la validità della conferma della password
	public static String checkConfermaPassword(String newPass, String confirmPass) {
		
		if (confirmPass.isBlank()) return "Confermare la password.";
	    if (!newPass.equals(confirmPass)) return "Password non corrispondenti.";
	    
	    return "Verificata.";
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce l'aggiunta di nuovi user al file di testo che svolge il ruolo di "database"
	public static String addUser(String nome, String cognome, String username, String password, String confirmPass) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/files/utenti.txt", true));
		writer.write(username + "," + nome + "," + cognome + "," + password + ", , \n");
		writer.flush();
		writer.close();
		
		return "Utente registrato correttamente!";
	}	
}