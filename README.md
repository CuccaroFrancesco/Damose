# Damose
*Damose* è un'applicazione per desktop che permette a chi la utilizza di visualizzare le varie linee su cui transitano gli autobus di Roma, navigare tra linee e fermate diverse, e individuare in tempo reale determinati mezzi, conoscendone posizione e orari di arrivo.

L'applicazione è interamente sviluppata in *Java*, sfruttando IntelliJ IDEA come IDE. 

___

## Cose da fare:

### 🌐 Dati real-time:
- Caricare i dati relativi alla posizione, velocità e direzione dei mezzi
- Caricare i dati relativi a eventuali ritardi, eccezioni o altre modifiche straordinarie ai servizi
- Inserire dinamicamente i dati real-time
- Visualizzare messaggio di avviso se i dati real-time non sono disponibili (errori o mancanza di rete)

### 🎨 Estetica:
- Personalizzare l'aspetto dei waypoint (fermate di agenzie diverse, mezzi, ecc. ecc.)

### 🚌 Mezzi:
- Visualizzazione della posizione stimata sulla mappa (aggiornata ogni 30 secondi)
- Visualizzazione della direzione di transito

Alla selezione di un determinato mezzo, dovrebbero diventare accessibili le seguenti informazioni:
- ID del mezzo
- fermata verso la quale si sta dirigendo
- linea lungo la quale sta transitando
- quantità di posti liberi a bordo (SE DISPONIBILE)
- velocità del mezzo
- eventuale ritardo del mezzo

### 〽️ RoutePanel:
Da implementare:
- Mezzi attualmente sulla linea

### 📊 Statistiche:
- Percentuale di corse in ritardo per linea
- Percentuale di corse puntuali per linea
- Percentuale di corse cancellate per linea
- Numero di mezzi attualmente in transito per linea
- Implementare la classe StatsPanel

### 🚏 StopPanel:
Da implementare:
- se presenti, prossimi arrivi con linea di appartenenza, orario di arrivo e direzione in maniera statica
- se online, aggiornare i prossimi arrivi confrontandoli con i dati realtime
- ritardo tipico dei mezzi per tale fermata

### 🧑‍💼 Utente:
- Impostazioni per l'utente:
  - Cambiare tema
  - Liberare la cache
  - Visualizzare solo fermate e linee preferite
___

## 🧪 TESTING UNITARIO
...
___

## ⚒️ BUG E MIGLIORAMENTI
- Creare un WaypointPainter personalizzato, per distinguere tra fermate generiche, fermate preferite (giallo) e fermate selezionate in un determinato momento (rosso)
- Rendere interattivi i waypoint delle fermate (se premuto, creare lo stopPanel corrispondente)