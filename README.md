# Damose
Damose è un'applicazione per il tracciamento e la consultazione della rete di trasporto pubblico della città di Roma. È progettata per garantire l'accesso alle informazioni di viaggio mantenendo la continuità d'uso sia in presenza di una connessione internet che in completa assenza di rete.

## Funzionalità Principali
**Consultazione Offline Avanzata:** Navigazione dell'intero catalogo delle linee (autobus, tram, metropolitana), visualizzazione della sequenza delle fermate per ogni percorso e accesso agli orari programmati (feriali e festivi) senza alcuna necessità di connessione internet.

**Tracciamento Live e Previsioni:** In modalità online, l'app sfrutta i flussi GTFS Real-time per mostrare la posizione dei veicoli in transito, stimare i tempi di attesa effettivi alle fermate e segnalare i ritardi rispetto alla tabella di marcia ufficiale.

**Ricerca Intelligente e Veloce:** Motore di ricerca rapido per individuare istantaneamente fermate specifiche (tramite nome o codice identificativo) e per filtrare le linee di trasporto di interesse.

**Avvisi di Servizio (Service Alerts):** Ricezione e visualizzazione delle allerte relative a scioperi, deviazioni di percorso, fermate soppresse o interruzioni temporanee della rete gestita da Roma Mobilità.

**Gestione Ibrida dei Dati:** Sincronizzazione fluida tra il grande volume di dati statici locali e i pacchetti leggeri in tempo reale, ottimizzando il consumo di rete e massimizzando la reattività dell'interfaccia.

**Focus su Roma:** Struttura delle classi e algoritmi di parsing calibrati specificamente per gestire le dimensioni, le metriche e le peculiarità strutturali del complesso dataset del trasporto pubblico romano.

## Tecnologie e Architettura
**Linguaggio:** Java

**Gestione Dati:** Parsing e strutturazione avanzata dei feed GTFS (Static e Real-time) per mappare fermate, corse e variazioni di orario.

## Requisiti e Installazione
Assicurati di avere installato un ambiente Java (JDK 25 o superiore) sul tuo sistema.

## Gestione dei Dataset GTFS
Per funzionare correttamente in modalità offline, l'applicazione include il caricamento preventivo dei file GTFS statici (es. routes.txt, stops.txt, trips.txt, stop_times.txt). I file testuali del dataset situati all'interno della directory dedicata possono essere aggiornati con i dati ufficiali scaricabili al link [RomaMobilita](https://romamobilita.it/sistemi-e-tecnologie/open-data/#gtfs-general-transit-feed-specification).
