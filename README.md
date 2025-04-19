# Damose
*Damose* è un'applicazione per desktop che permette a chi la utilizza di visualizzare le varie linee su cui transitano gli autobus di Roma, navigare tra linee e fermate diverse, e individuare in tempo reale determinati mezzi, conoscendone posizione e orari di arrivo.

L'applicazione è interamente sviluppata in *Java*, sfruttando Eclipse come IDE. 

___

## Cose da fare:

#### Dati real-time:
- Caricare i dati relativi alla posizione, velocità e direzione dei mezzi (implementare relativi pannelli)
- Caricare i dati relativi a eventuali ritardi, eccezioni o altre modifiche straordinarie ai servizi
- Inserire dinamicamente i dati real-time
- Visualizzare messaggio di avviso se i dati real-time non sono disponibili (errori o mancanza di rete)

#### Dati statici:
- Caricare i dati relativi agli orari delle corse
- Caricare i dati relativi alle linee
- Caricare i dati relativi alle shapes

#### Estetica:
- Creare un logo per l'applicazione
- Creare una schermata di caricamento all'avvio(?) (guadagnerebbe tempo per il caricamento dei dati)
- Inserire animazioni per il pannello utente
- Personalizzare l'aspetto dei waypoint (fermate di agenzie diverse, mezzi, ecc. ecc.)

#### Fermate:
Alla selezione di una determinata fermata, dovrebbero diventare accessibili le seguenti informazioni:
- informazioni identificative
	- nome
	- codice
	- ID
	- tipo
	- indirizzo
- linee passanti per tale fermata
- se presente, mezzo in avvicinamento con linea di appartenenza e orario di arrivo
- ritardo tipico dei mezzi per tale fermata
- (OPZIONALE) vicinanza a punti di interesse

#### Linee:
- Implementare una classe per le linee (con relativi pannelli)
	- Costruttore
	- Come attributi, i dati di una determinata linea
	- Metodi get per ottenere singoli attributi di una linea
	- Metodo per stampare visivamente la linea sulla mappa

#### Mappa:
- Conservare lo zoom e la posizione quando si passa da un tipo di mappa all'altro
- Implementare metodo che zoomma automaticamente su un punto di interesse selezionato
- Inserire filtri per cosa visualizzare sulla mappa
	- fermate
		- fermate con alto tasso di puntualità
		- fermate con medio tasso di puntualità
		- fermate con basso tasso di puntualità
		- distinzione tra agenzie
		- distinzione tra tipo di fermata
	- mezzi
		- distinzione tra tipo di mezzo
	- linee
- Modificare lo zoom-threshold per cui sono visibili le fermate(?)

#### Mezzi:
- Visualizzazione della posizione stimata sulla mappa (aggiornata ogni 30 secondi)
- Visualizzazione della direzione di transito

Alla selezione di un determinato mezzo, dovrebbero diventare accessibili le seguenti informazioni:
- ID del mezzo
- fermata verso la quale si sta dirigendo
- linea lungo la quale sta transitando
- quantità di posti liberi a bordo (SE DISPONIBILE)
- velocità del mezzo
- eventuale ritardo del mezzo

#### Navbar:
- Aggiungere funzionalità per la barra di ricerca
- Perfezionare il pulsante per la sezione utente

#### Ricerca:
- Ricerca di una fermata per nome e codice
- Ricerca di una linea per numero e nome
- Ricerca di un mezzo per ID o linea di appartenenza

#### Statistiche:
- Percentuale di corse in ritardo per linea
- Percentuale di corse puntuali per linea
- Percentuale di corse cancellate per linea
- Numero di mezzi attualmente in transito per linea

#### Utente:
- Icona del profilo
- Impostazioni di personalizzazione per l'utente
	- modifica del colore della navbar
	- modifica del colore del pannello utente
	- scelta dei dati da visualizzare per linee, fermate e mezzi
- Inserimento e visualizzazione di fermate e linee preferite
- Pulsante di logout
___

## TESTING UNITARIO
...
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