package damose;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class Utente {
	
	private String nome, cognome, username, password;
	private double spawnPointLat, spawnPointLon;
	private boolean isLogged, fermatePreferiteToggleStatus, centroAutoSpawnPointToggleStatus;
	private List<String> lineePreferite, fermatePreferite;
	private PreferitiObserver observer;
	
	
	// Metodi get e set per il nome associato all'utente
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	// Metodi get e set per il cognome associato all'utente
	public String getCognome() {
		return this.cognome;
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
		return this.lineePreferite;
	}

	public void setLineePreferite(List<String> lineePreferite) {
		this.lineePreferite = lineePreferite.equals(" ") ? new ArrayList<>() : lineePreferite;
	}
	
	
	// Metodi get e set per le fermate preferite dell'utente
	public List<String> getFermatePreferite() {
		return this.fermatePreferite;
	}
	
	public void setFermatePreferite(List<String> fermatePreferite) {
		this.fermatePreferite = fermatePreferite.equals(" ") ? new ArrayList<>() : fermatePreferite;
	}


	// Metodi get e set per la preferenza dell'impostazione "Visualizza solo fermate preferite"
	public boolean getFermatePreferiteToggleStatus() {
		return this.fermatePreferiteToggleStatus;
	}

	public void setFermatePreferiteToggleStatus(boolean fermatePreferiteToggleStatus) {
		this.fermatePreferiteToggleStatus = fermatePreferiteToggleStatus;
	}


	// Metodi get e set per la latitudine dello SpawnPoint dell'utente
	public double getSpawnPointLat() {
		return this.spawnPointLat;
	}

	public void setSpawnPointLat(double spawnPointLat) {
		this.spawnPointLat = spawnPointLat;
	}


	// Metodi get e set per la longitudine dello SpawnPoint dell'utente
	public double getSpawnPointLon() {
		return this.spawnPointLon;
	}

	public void setSpawnPointLon(double spawnPointLon) {
		this.spawnPointLon = spawnPointLon;
	}


	// Metodi get e set per la preferenza dell'impostazione "Centramento automatico sullo SpawnPoint"
	public boolean getCentroAutoSpawnPointToggleStatus() {
		return this.centroAutoSpawnPointToggleStatus;
	}

	public void setCentroAutoSpawnPointToggleStatus(boolean centroAutoSpawnPointToggleStatus) {
		this.centroAutoSpawnPointToggleStatus = centroAutoSpawnPointToggleStatus;
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
		if (observer != null) observer.onPreferitiChanged();
	}


// ---------------------------------------------------------------------------------------------


	// Metodo per aggiornare i dati dell'utente loggato
	public void aggiornaUtente(List<String> lineePreferite, List<String> fermatePreferite, boolean fermatePreferiteToggleStatus, double spawnPointLat, double spawnPointLon, boolean centroAutoSpawnPointToggleStatus) {
		
		this.setLineePreferite(lineePreferite);
		this.setFermatePreferite(fermatePreferite);
		this.setFermatePreferiteToggleStatus(fermatePreferiteToggleStatus);
		this.setSpawnPointLat(spawnPointLat);
		this.setSpawnPointLon(spawnPointLon);
		this.setCentroAutoSpawnPointToggleStatus(centroAutoSpawnPointToggleStatus);

		String fermatePreferiteToggleStatusString = fermatePreferiteToggleStatus ? "1" : "0";
		String centroAutoSpawnPointToggleStatusString = centroAutoSpawnPointToggleStatus ? "1" : "0";

		try {
			Path path = Paths.get(Frame.getDamoseDirectory() + File.separator + "files" + File.separator + "utenti.txt");
			List<String> righe = Files.readAllLines(path);

			for (int i = 0; i < righe.size(); i++) {
				if (righe.get(i).startsWith(this.username)) {
					righe.set(i, this.username + "," + this.nome + "," + this.cognome + "," + this.password + "," +
							     String.join("-", lineePreferite) + "," +
							     String.join("-", fermatePreferite) + "," +
							     fermatePreferiteToggleStatusString + "," +
							     spawnPointLat + "," + spawnPointLon + "," +
							     centroAutoSpawnPointToggleStatusString);
					break;
				}
			}

			Files.write(path, righe);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		notificaObserver();
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce l'accesso a un utente già esistente
	public String accedi(String username, String password) throws IOException, URISyntaxException {
		
		if (username.isBlank()) return "Username non inserito.";
		
		BufferedReader reader = new BufferedReader(new FileReader(Frame.getDamoseDirectory() + File.separator + "files" + File.separator + "utenti.txt"));
        String riga;
        
        while ((riga = reader.readLine()) != null) {

         	List<String> dati = new ArrayList<>(List.of(riga.split(",", -1)));

            if (!dati.isEmpty() && dati.get(0).equals(username.trim())) {
            	
            	if (dati.get(3).trim().equals(password)) {
            		
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

					if (dati.get(6).equals("0")) this.setFermatePreferiteToggleStatus(false);
					else if (dati.get(6).equals("1")) this.setFermatePreferiteToggleStatus(true);

					if (!dati.get(7).isBlank()) this.setSpawnPointLat(Double.parseDouble(dati.get(7).trim()));
					else this.setSpawnPointLat(0.0);

					if (!dati.get(8).isBlank()) this.setSpawnPointLon(Double.parseDouble(dati.get(8).trim()));
					else this.setSpawnPointLon(0.0);

					if (dati.get(9).equals("0")) this.setCentroAutoSpawnPointToggleStatus(false);
					else if (dati.get(9).equals("1")) this.setCentroAutoSpawnPointToggleStatus(true);

            		return "Verificata.";
            		
            	} else return "Password errata.";
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
		this.fermatePreferiteToggleStatus = false;
		this.spawnPointLat = 0.0;
		this.spawnPointLon = 0.0;
		this.centroAutoSpawnPointToggleStatus = false;
		this.isLogged = false;
	}
}