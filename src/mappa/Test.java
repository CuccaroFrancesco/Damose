package mappa;


public class Test {
    public static void main(String[] args) 
    {
        // Esempio di condizione
        boolean condizione = true; // Cambia questa condizione per testare

        // Codici ANSI per i colori
        String verde = "\u001B[32m"; // Verde
        String rosso = "\u001B[31m"; // Rosso
        String reset = "\u001B[0m";  // Reset del colore

        if (condizione) {
            System.out.println(verde + "Questo è un testo verde" + reset);
        } else {
            System.out.println(rosso + "Questo è un testo rosso" + reset);
        }
    }
}
