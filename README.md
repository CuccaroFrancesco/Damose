# Damose
*Damose* è un'applicazione per desktop che permette a chi la utilizza di visualizzare le varie linee su cui transitano gli autobus di Roma, navigare tra linee e fermate diverse, e individuare in tempo reale determinati mezzi, conoscendone posizione e orari di arrivo.

L'applicazione è interamente sviluppata in *Java*, sfruttando Eclipse come IDE. 

___

# Cose da fare:

### Sezione utente:
- Icona del profilo
- Inserimento e visualizzazione di fermate e linee preferite
- Pulsante di logout

### Dati real-time:
- Inserire dinamicamente i dati real-time

### Dati statici:
- Inserire i dati statici

### Mappa:
- Visualizzare le fermate
- Visualizzare le linee
- Visualizzare i mezzi

### Estetica:
- Inserire animazioni per il pannello utente
___

# Test possibili

- Verificare se i dati sono nel formato corretto
- Verificare i collegamenti tra i vari datasets
- Testare l'efficienza dell'applicazione
- Altri tipi di test

___

# Classi da sviluppare

### Utente:
- ID;
- nome;
- email;
- posizione;
- password;
- posizioneAttuale;
- listaLineePreferite; (in locale)
- listaFermatePreferite; (in locale)

### Mezzo di trasporto:
- ID;
- numeroLinea;
- tipoDiMezzo; (bus, metro, tram)
- posizione;
- statoDiTransito;
- affollamento;
- tempoDiRitardo;
- orarioDiPartenza;
- orariDiArrivoPrevisti; (array, per fermata, visualizzato con ritardo ma mai modificato)

### Fermata:
- ID;
- nome; 
- posizione;
- listaLinee;

### Linea
- nome;
- numero;
- listaFermate;
- listaPuntualità; (anticipo, ritardo, puntuali, annullati) —> [23,15,76,4]; (rendere percentuale)
- colore;

### Mappa:
- Zoom;
- Centrare alla posizione attuale;
- Switch tra mappa normale e satellitare;
- Inserimento di waypoint arbitrari;
- Icone interattive (se clicco sulla fermata, vedo tutte le linee passanti per essa);