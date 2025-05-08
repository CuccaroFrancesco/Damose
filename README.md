# Damose
*Damose* è un'applicazione per desktop che permette a chi la utilizza di visualizzare le varie linee su cui transitano gli autobus di Roma, navigare tra linee e fermate diverse, e individuare in tempo reale determinati mezzi, conoscendone posizione e orari di arrivo.

L'applicazione è interamente sviluppata in *Java*, sfruttando Eclipse come IDE. 

___

## Cose da fare:

#### Dati real-time:
- Caricare i dati relativi alla posizione, velocità e direzione dei mezzi
- Caricare i dati relativi a eventuali ritardi, eccezioni o altre modifiche straordinarie ai servizi
- Inserire dinamicamente i dati real-time
- Visualizzare messaggio di avviso se i dati real-time non sono disponibili (errori o mancanza di rete)

#### Estetica:
- Creare un logo per l'applicazione
- Creare una schermata di caricamento all'avvio (guadagnerebbe tempo per il caricamento dei dati)
- Inserire animazioni per i vari pannelli pop-up (scorrimento o fade)
- Inserire un frame di transizione (cuore bianco leggermente più grande) tra cuore pieno e cuore vuoto nel btnPreferiti
- Personalizzare l'aspetto dei waypoint (fermate di agenzie diverse, mezzi, ecc. ecc.)

#### StopPanel:
Alla selezione di una determinata fermata, dovrebbero diventare accessibili le seguenti informazioni:
- informazioni identificative
	- nome
	- codice
	- coordinate
	- ID
	- tipo 
	- accessibilità
- linee passanti per tale fermata
- se presente, prossimi arrivi con linea di appartenenza e orario di arrivo e direzione
- ritardo tipico dei mezzi per tale fermata
- (OPZIONALE MA SAREBBE BELLO) vicinanza a punti di interesse

#### LineaPanel:
Da implementare:
- Mezzi attualmente sulla linea

#### Mappa:
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

#### Ricerca:
- Ricerca di una fermata per nome e codice
- Ricerca di una linea per numero e nome

#### Statistiche:
- Percentuale di corse in ritardo per linea
- Percentuale di corse puntuali per linea
- Percentuale di corse cancellate per linea
- Numero di mezzi attualmente in transito per linea

#### Utente:
- Icona del profilo
- Impostazioni di personalizzazione per l'utente:
  - Cambiare tema
  - Liberare la cache
  - Visualizzare solo fermate e linee preferite
- Pulsante di logout
___

## TESTING UNITARIO
...
___