package test;

import com.google.transit.realtime.GtfsRealtime;
import damose.DatiGTFS;
import damose.Frame;
import org.junit.jupiter.api.*;
import org.onebusaway.gtfs.model.*;
import org.testng.annotations.BeforeTest;

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


    @Test
    public void collectionViaggi_NonDeveEssereVuota() {
        assertFalse(viaggi.isEmpty(), "La collection dei viaggi non deve essere vuota.");
    }

    @Test
    public void collectionLinee_NonDeveEssereVuota() {
        assertFalse(linee.isEmpty(), "La collection delle linee non deve essere vuota.");
    }

    @Test
    public void collectionFermate_NonDeveEssereVuota() {
        assertFalse(fermate.isEmpty(), "La collection delle fermate non deve essere vuota.");
    }

    @Test
    public void ricercaLinee_StringaVuota_DeveRestituireNull() {
        assertNull(datiGTFS.cercaLinee(""));
    }

    @Test
    public void ricercaLinee_NonDeveCambiarePerSpaziInizioFine() {
        assertEquals(datiGTFS.cercaLinee(" 712 "), datiGTFS.cercaLinee("712"));
    }

    @Test
    public void ricercaFermate_NonDeveCambiarePerSpazi() {
        assertEquals(datiGTFS.cercaFermate("71 "), datiGTFS.cercaFermate("71"));
    }

    @Test
    public void ricercaFermate_DiversaDa_riceraLinee() {
        assertNotEquals(datiGTFS.cercaLinee("71"), datiGTFS.cercaFermate("71"));
    }

    @Test
    public void ricercaFermate_StringaVuota_DeveRestituireNull() {
        assertNull(datiGTFS.cercaFermate(""));
    }

    @Test
    public void ricercaFermate_inputPreciso_DeveRestituireUnSoloRisultato() {
        assertEquals(1,datiGTFS.cercaFermate("71033").size());
    }

    @Test
    public void ricercaLinee_inputPreciso_DeveRestituireUnSoloRisultato() {
        assertEquals(1,datiGTFS.cercaLinee("MEA").size());
    }

    @Test
    public void ricercaLineaConID_inputInesistente_DeveRestituireNull() {
        assertNull(datiGTFS.cercaLineaByID("PROVALINEA"));
    }

    @Test
    public void ricercaFermataConID_inputInesistente_DeveRestituireNull() {
        assertNull(datiGTFS.cercaFermataByID("PROVAFERMATA"));
    }

    @Test
    public void getFermatePerViaggio_viaggioNull_DeveRestituireNull() {
        assertNull(datiGTFS.getFermatePerViaggio(null));
    }

    @Test
    public void ogniFermata_DeveAvereAlmenoUnaLineaPassante() {
        for(Stop fermata: fermate) {
            assertNotNull(fermata);
            assertFalse(datiGTFS.getLineePassantiPerFermata(fermata).isEmpty(),  "Nessuna linea trovata per la fermata: " + fermata.getId());
        }
    }

    @Test
    public void ogniLinea_DeveAvereAlmenoUnViaggio() {
        for(Route linea: linee) {
            assertNotNull(linee);
            assertFalse(datiGTFS.getViaggiDaVisualizzare(linea).isEmpty(), "Nessun viaggio trovato per la linea: " + linea.getId());
        }
    }

    @Test
    public void ogniViaggio_DeveAvereAlmenoUnaFermata() {
        for(Trip viaggio: viaggi) {
            assertNotNull(viaggio);
            assertFalse(datiGTFS.getFermatePerViaggio(viaggio).isEmpty(), "Nessuna fermata trovata per il viaggio: " + viaggio.getId());
        }
    }

    @Test
    public void getLineePassantiPerFermata_fermataNull_DeveRestituireNull() {
        assertNull(datiGTFS.getLineePassantiPerFermata(null));
    }

    @Test
    public void getViaggiDaVisualizzare_lineaNull_DeveRestituireNull() {
        assertNull(datiGTFS.getViaggiDaVisualizzare(null));
    }


}
