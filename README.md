# Damose
 Progetto Java per trasporti di Roma


# Classi da sviluppare

##Utente:
- ID;
- nome;
- email;
- posizione;
- password;
- posizioneAttuale;
- listaLineePreferite; (in locale)
- listaFermatePreferite; (in locale)

##Mezzo di trasporto:
- ID;
- numeroLinea;
- tipoDiMezzo; (bus, metro, tram)
- posizione;
- statoDiTransito;
- affollamento;
- tempoDiRitardo;
- orarioDiPartenza;
- orariDiArrivoPrevisti; (array, per fermata, visualizzato con ritardo ma mai modificato)

##Fermata:
- ID;
- nome; 
- posizione;
- listaLinee;
- Linea
- nome;
- numero;
- listaFermate;
- listaPuntualità; (anticipo, ritardo, puntuali, annullati) —> [23,15,76,4]; (rendere percentuale)
- colore;

##Mappa:
- Zoom;
- Centrare alla posizione attuale;
- Switch tra mappa normale e satellitare;
- Inserimento di waypoint arbitrari;
- Icone interattive (se clicco sulla fermata, vedo tutte le linee passanti per essa);