package damose;

import org.junit.jupiter.api.*;
import java.io.*;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test per la classe Registrazione")
public class RegistrazioneTest {

    @BeforeAll
    public static void setup() throws IOException, URISyntaxException {
        File file = new File(Frame.getDamoseDirectory(), "files" + File.separator + "utenti.txt");
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("utenteEsistente,Nome,Cognome,password, , ,0,0.0,0.0,0\n");
        }
    }

    @Nested
    @DisplayName("Test checkNome")
    class CheckNomeTests {
        @Test
        @DisplayName("Nome valido")
        void nomeValido() {
            assertEquals("Verificata.", Registrazione.checkNome("Mario"));
        }

        @Test
        @DisplayName("Nome vuoto")
        void nomeVuoto() {
            assertEquals("Nome non inserito.", Registrazione.checkNome(""));
        }

        @Test
        @DisplayName("Nome con solo spazi")
        void nomeSpazi() {
            assertEquals("Nome non inserito.", Registrazione.checkNome("   "));
        }
    }

    @Nested
    @DisplayName("Test checkCognome")
    class CheckCognomeTests {
        @Test
        @DisplayName("Cognome valido")
        void cognomeValido() {
            assertEquals("Verificata.", Registrazione.checkCognome("Rossi"));
        }

        @Test
        @DisplayName("Cognome vuoto")
        void cognomeVuoto() {
            assertEquals("Cognome non inserito.", Registrazione.checkCognome(""));
        }
    }

    @Nested
    @DisplayName("Test checkUsername")
    class CheckUsernameTests {
        @Test
        @DisplayName("Username vuoto")
        void usernameVuoto() throws IOException, URISyntaxException {
            assertEquals("Username non inserito.", Registrazione.checkUsername(""));
        }

        @Test
        @DisplayName("Username già in uso")
        void usernameGiaInUso() throws IOException, URISyntaxException {
            assertEquals("Username già in uso.", Registrazione.checkUsername("utenteEsistente"));
        }

        @Test
        @DisplayName("Username disponibile")
        void usernameDisponibile() throws IOException, URISyntaxException {
            assertEquals("Verificata.", Registrazione.checkUsername("utenteNuovo"));
        }
    }

    @Nested
    @DisplayName("Test checkPassword")
    class CheckPasswordTests {
        @Test
        @DisplayName("Password vuota")
        void passwordVuota() {
            assertEquals("Password non inserita.", Registrazione.checkPassword(""));
        }

        @Test
        @DisplayName("Password troppo corta")
        void passwordCorta() {
            assertEquals("Lunghezza minima: 8 caratteri.", Registrazione.checkPassword("Ab1!"));
        }

        @Test
        @DisplayName("Manca maiuscola")
        void mancaMaiuscola() {
            assertEquals("Inserire almeno una lettera maiuscola.", Registrazione.checkPassword("abc1!def"));
        }

        @Test
        @DisplayName("Manca minuscola")
        void mancaMinuscola() {
            assertEquals("Inserire almeno una lettera minuscola.", Registrazione.checkPassword("ABC1!DEF"));
        }

        @Test
        @DisplayName("Manca numero")
        void mancaNumero() {
            assertEquals("Inserire almeno un numero.", Registrazione.checkPassword("Abc!defg"));
        }

        @Test
        @DisplayName("Manca simbolo")
        void mancaSimbolo() {
            assertEquals("Inserire almeno un simbolo: /*-+!£$%&=?€", Registrazione.checkPassword("Abc12345"));
        }

        @Test
        @DisplayName("Password valida")
        void passwordValida() {
            assertEquals("Verificata.", Registrazione.checkPassword("Abc1!def"));
        }
    }

    @Nested
    @DisplayName("Test checkConfermaPassword")
    class CheckConfermaPasswordTests {
        @Test
        @DisplayName("Conferma vuota")
        void confermaVuota() {
            assertEquals("Confermare la password.", Registrazione.checkConfermaPassword("pass", ""));
        }

        @Test
        @DisplayName("Password non corrispondenti")
        void nonCorrispondono() {
            assertEquals("Password non corrispondenti.", Registrazione.checkConfermaPassword("pass1", "pass2"));
        }

        @Test
        @DisplayName("Password corrispondenti")
        void corrispondono() {
            assertEquals("Verificata.", Registrazione.checkConfermaPassword("pass123", "pass123"));
        }
    }
}
