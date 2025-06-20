package damose;

import org.junit.jupiter.api.*;
import org.onebusaway.gtfs.model.*;

import java.io.File;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;



public class DatiGTFSTest {

    private static DatiGTFS datiGTFS;
    private static Collection<Stop> fermate;
    private static Collection<Trip> viaggi;
    private static Collection<Route> linee;

    @BeforeAll
    public static void setup() throws Exception {
        datiGTFS = new DatiGTFS();
        datiGTFS.creaCaricamento();
        datiGTFS.caricaDatiStaticiGTFS(new File(Frame.getDamoseDirectory(), "staticGTFS"));

        viaggi = datiGTFS.getViaggi();
        fermate = datiGTFS.getFermate();
        linee = datiGTFS.getLinee();
    }

    @Test
    @DisplayName("Dati realtime: null senza rete / not null con rete")
    public void datiRealtime_NullSenzaRete_NotNullConRete() {
        try {
            datiGTFS.caricaAlertGTFS();
            datiGTFS.caricaTripUpdatesGTFS();
            datiGTFS.caricaVehiclePositionsGTFS();

            assertNotNull(datiGTFS.getTripUpdates(), "TripUpdates non dovrebbe essere null se la rete è disponibile.");
            assertNotNull(datiGTFS.getVehiclePositions(), "VehiclePositions non dovrebbe essere null se la rete è disponibile.");
            assertNotNull(datiGTFS.getAlert(), "Alert non dovrebbe essere null se la rete è disponibile.");
        } catch (Exception e) {
            assertNull(datiGTFS.getTripUpdates(), "TripUpdates dovrebbe essere null in caso di mancanza di rete.");
            assertNull(datiGTFS.getVehiclePositions(), "VehiclePositions dovrebbe essere null in caso di mancanza di rete.");
            assertNull(datiGTFS.getAlert(), "Alert dovrebbe essere null in caso di mancanza di rete.");
        }
    }

    @Nested
    @DisplayName("Test sulle collection principali")
    class CollectionTests {
        @Test
        @DisplayName("La collection dei viaggi non deve essere vuota")
        public void collectionViaggi_NonDeveEssereVuota() {
            assertFalse(viaggi.isEmpty());
        }

        @Test
        @DisplayName("La collection delle linee non deve essere vuota")
        public void collectionLinee_NonDeveEssereVuota() {
            assertFalse(linee.isEmpty());
        }

        @Test
        @DisplayName("La collection delle fermate non deve essere vuota")
        public void collectionFermate_NonDeveEssereVuota() {
            assertFalse(fermate.isEmpty());
        }
    }

    @Nested
    @DisplayName("Test ricerca linee")
    class RicercaLineeTests {
        @Test
        @DisplayName("Ricerca linee con stringa vuota deve restituire null")
        public void ricercaLinee_StringaVuota_DeveRestituireNull() {
            assertNull(datiGTFS.cercaLinee(""));
        }

        @Test
        @DisplayName("Ricerca linee non deve cambiare per spazi inizio/fine")
        public void ricercaLinee_NonDeveCambiarePerSpaziInizioFine() {
            assertEquals(datiGTFS.cercaLinee(" 712 "), datiGTFS.cercaLinee("712"));
        }

        @Test
        @DisplayName("Ricerca linee con input preciso deve restituire un solo risultato")
        public void ricercaLinee_inputPreciso_DeveRestituireUnSoloRisultato() {
            assertEquals(1,datiGTFS.cercaLinee("MEA").size());
        }

        @Test
        @DisplayName("Ricerca linea con ID inesistente deve restituire null")
        public void ricercaLineaConID_inputInesistente_DeveRestituireNull() {
            assertNull(datiGTFS.cercaLineaByID("PROVALINEA"));
        }
    }

    @Nested
    @DisplayName("Test ricerca fermate")
    class RicercaFermateTests {
        @Test
        @DisplayName("Ricerca fermate non deve cambiare per spazi inizio/fine")
        public void ricercaFermate_NonDeveCambiarePerSpazi() {
            assertEquals(datiGTFS.cercaFermate("71 "), datiGTFS.cercaFermate("71"));
        }

        @Test
        @DisplayName("Ricerca fermate dev'essere diversa da ricerca linee")
        public void ricercaFermate_DiversaDa_riceraLinee() {
            assertNotEquals(datiGTFS.cercaLinee("71"), datiGTFS.cercaFermate("71"));
        }

        @Test
        @DisplayName("Ricerca fermate con stringa vuota deve restituire null")
        public void ricercaFermate_StringaVuota_DeveRestituireNull() {
            assertNull(datiGTFS.cercaFermate(""));
        }

        @Test
        @DisplayName("Ricerca fermate con input preciso deve restituire un solo risultato")
        public void ricercaFermate_inputPreciso_DeveRestituireUnSoloRisultato() {
            assertEquals(1,datiGTFS.cercaFermate("71033").size());
        }

        @Test
        @DisplayName("Ricerca fermate con input inesistente deve restituire null")
        public void ricercaFermataConID_inputInesistente_DeveRestituireNull() {
            assertNull(datiGTFS.cercaFermataByID("PROVAFERMATA"));
        }
    }

    @Nested
    @DisplayName("Test viaggi e fermate")
    class ViaggiFermateTests {
        @Test
        @DisplayName("Se il viaggio è null getFermatePerViaggio deve restituire null")
        public void getFermatePerViaggio_viaggioNull_DeveRestituireNull() {
            assertNull(datiGTFS.getFermatePerViaggio(null));
        }

        @Test
        @DisplayName("Ogni fermata deve avere almeno una linea passante")
        public void ogniFermata_DeveAvereAlmenoUnaLineaPassante() {
            for(Stop fermata: fermate) {
                assertNotNull(fermata);
                assertFalse(datiGTFS.getLineePassantiPerFermata(fermata).isEmpty(),
                        "Nessuna linea trovata per la fermata: " + fermata.getId());
            }
        }

        @Test
        @DisplayName("Ogni linea deve avere almeno un viaggio")
        public void ogniLinea_DeveAvereAlmenoUnViaggio() {
            for(Route linea: linee) {
                assertNotNull(linea);
                assertFalse(datiGTFS.getViaggiDaVisualizzare(linea).isEmpty(),
                        "Nessun viaggio trovato per la linea: " + linea.getId());
            }
        }

        @Test
        @DisplayName("Ogni viaggio deve avere almeno una fermata")
        public void ogniViaggio_DeveAvereAlmenoUnaFermata() {
            for(Trip viaggio: viaggi) {
                assertNotNull(viaggio);
                assertFalse(datiGTFS.getFermatePerViaggio(viaggio).isEmpty(),
                        "Nessuna fermata trovata per il viaggio: " + viaggio.getId());
            }
        }
    }

    @Nested
    @DisplayName("Test casi nulli")
    class NullCasesTests {
        @Test
        @DisplayName("Se la fermata è null getLineePassantiPerFermata deve restituire null")
        public void getLineePassantiPerFermata_fermataNull_DeveRestituireNull() {
            assertNull(datiGTFS.getLineePassantiPerFermata(null));
        }

        @Test
        @DisplayName("Se la linea è null getViaggiDaVisualizzare deve restituire null")
        public void getViaggiDaVisualizzare_lineaNull_DeveRestituireNull() {
            assertNull(datiGTFS.getViaggiDaVisualizzare(null));
        }
    }
}
