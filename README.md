# Damose
*Damose* è un'applicazione per desktop che permette a chi la utilizza di visualizzare le varie linee su cui transitano gli autobus di Roma, navigare tra linee e fermate diverse, e individuare in tempo reale determinati mezzi, conoscendone posizione e orari di arrivo.

L'applicazione è interamente sviluppata in *Java*, sfruttando Eclipse come IDE. 

___

## Cose da fare:

#### Dati real-time:
- Inserire dinamicamente i dati real-time
- Caricare i dati relativi alla posizione, velocità e direzione dei mezzi (implementare relativi pannelli)
- Caricare i dati relativi a eventuali ritardi, eccezioni o altre modifiche straordinarie ai servizi

#### Dati statici:
- Caricare i dati relativi agli orari delle corse
- Caricare i dati relativi alle linee
- Caricare i dati relativi alle shapes

#### Estetica:
- Creare un logo per l'applicazione
- Creare una schermata di caricamento all'avvio(?) (guadagnerebbe tempo per il caricamento dei dati)
- Inserire animazioni per il pannello utente
- Personalizzare l'aspetto dei waypoint (fermate di agenzie diverse, mezzi, ecc. ecc.)

#### Linee:
- Implementare una classe per le linee (con relativi pannelli)
	- Costruttore
	- Come attributi, i dati di una determinata linea
	- Metodi get per ottenere singoli attributi di una linea
	- Metodo per stampare visivamente la linea sulla mappa

#### Mappa:
- Conservare lo zoom e la posizione quando si passa da un tipo di mappa all'altro
- Implementare metodo che zoomma automaticamente su un punto di interesse selezionato
- Modificare lo zoom-threshold per cui sono visibili le fermate(?)

#### Navbar:
- Aggiungere funzionalità per la barra di ricerca
- Perfezionare il pulsante per la sezione utente

#### Statistiche:
- Implementare una classe per la gestione delle statistiche

#### Utente:
- Icona del profilo
- Inserimento e visualizzazione di fermate e linee preferite
- Pulsante di logout
___

## Test possibili
- Verificare se i dati sono nel formato corretto
- Verificare i collegamenti tra i vari datasets
- Testare l'efficienza dell'applicazione
- Altri tipi di test

___

## Classi da sviluppare

#### Utente:
- ID;
- nome;
- email;
- posizione;
- password;
- posizioneAttuale;
- listaLineePreferite; (in locale)
- listaFermatePreferite; (in locale)

#### Mezzo di trasporto:
- ID;
- numeroLinea;
- tipoDiMezzo; (bus, metro, tram)
- posizione;
- statoDiTransito;
- affollamento;
- tempoDiRitardo;
- orarioDiPartenza;
- orariDiArrivoPrevisti; (array, per fermata, visualizzato con ritardo ma mai modificato)

#### Fermata:
- ID;
- nome; 
- posizione;
- listaLinee;

#### Linea
- nome;
- numero;
- listaFermate;
- listaPuntualità; (anticipo, ritardo, puntuali, annullati) —> [23,15,76,4]; (rendere percentuale)
- colore;

#### Mappa:
- Zoom;
- Centrare alla posizione attuale;
- Switch tra mappa normale e satellitare;
- Inserimento di waypoint arbitrari;
- Icone interattive (se clicco sulla fermata, vedo tutte le linee passanti per essa);