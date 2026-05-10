// Defineix el paquet on s'ubica la classe, formant part de la capa de presentació (Vista).
package Vista;

// Importa la classe Scanner de Java per poder capturar l'entrada de l'usuari pel teclat.
import java.util.Scanner;

// Declaració de la classe 'Utils', que serveix per agrupar mètodes auxiliars o d'utilitat general.
public class Utils {
    // Crea una instància compartida (estàtica), inalterable (final) i privada de Scanner associada a la consola.
    // Això evita haver de crear un nou Scanner cada cop que es crida el mètode.
    private static final Scanner sc = new Scanner(System.in);

    // Mètode estàtic públic que s'utilitza per fer una pausa en l'execució de la interfície.
    public static void esperarEnter() {
        // Imprimeix un missatge informatiu precedit d'un salt de línia (\n) per separar-lo visualment.
        System.out.print("\nPrem ENTER per continuar...");
        // Llegeix la línia buida que es genera en prémer la tecla Intro (Enter), alliberant així la pausa.
        sc.nextLine();
    }
}
