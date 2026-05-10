// Defineix el paquet on s'ubica la classe, formant part de la capa de presentació (Vista).
package Vista;

// Importa els controladors encarregats d'obtenir i mostrar les dades d'escaladors, escoles, sectors i vies.
import Controller.Escalador.llistarEscalador;
import Controller.Escola.llistarEscola;
import Controller.Sector.llistarSector;
import Controller.Vies.llistarVies;
// Importa la utilitat Scanner per recollir el que l'usuari introdueix per teclat.
import java.util.Scanner;

// Declaració de la classe que gestiona el submenú orientat a mostrar llistats i consultes.
public class menuLlistar {
    // Mètode estàtic per invocar el menú directament sense necessitat d'instanciar la classe.
    public static void menu() {
        // Inicialitza l'objecte Scanner per llegir l'entrada de la consola.
        Scanner sc = new Scanner(System.in);
        // Variable booleana de control per mantenir l'usuari dins d'aquest submenú.
        boolean tornar = false;

        // Bucle que es repeteix fins que la variable 'tornar' passi a ser certa (true).
        while (!tornar) {
            // Bloc on s'imprimeix per pantalla la interfície amb totes les opcions estructurades.
            System.out.println("\n---------------LLISTAR / CONSULTAR---------------");
            // Opcions de lectura bàsica del CRUD.
            System.out.println("--- CRUD ---");
            System.out.println("1. Un escalador");
            System.out.println("2. Tots els escaladors");
            System.out.println("3. Una escola");
            System.out.println("4. Totes les escoles");
            System.out.println("5. Un sector");
            System.out.println("6. Tots els sectors");
            System.out.println("7. Una via");
            System.out.println("8. Totes les vies");
            // Opcions de consultes avançades a la base de dades.
            System.out.println("--- CONSULTES ESPECIFIQUES ---");
            System.out.println("9.  Vies disponibles d'una escola");
            System.out.println("10. Cercar vies per rang de dificultat");
            System.out.println("11. Cercar vies per estat");
            System.out.println("12. Escoles amb restriccions actives");
            System.out.println("13. Sectors amb mes de X vies disponibles");
            System.out.println("14. Escaladors amb el mateix nivell maxim");
            System.out.println("15. Vies que han passat a APTE recentment");
            System.out.println("16. Vies mes llargues d'una escola");
            System.out.println("0.  TORNAR");
            System.out.print("OPCIO: ");

            // Inici del bloc try-catch per evitar el tancament abrupte del programa si l'entrada no és correcta.
            try {
                // Recull la línia escrita, la converteix a enter i ho desa a 'op'.
                int op = Integer.parseInt(sc.nextLine());
                // S'avalua l'opció i s'actua en conseqüència.
                switch (op) {
                    // Crides als mètodes de llistat simples (obtenir un sol element de la base de dades).
                    case 1  -> llistarEscalador.llistarUn();
                    // Crides als mètodes de llistat massiu (obtenir tots els elements).
                    case 2  -> llistarEscalador.llistarTots();
                    case 3  -> llistarEscola.llistarUna();
                    case 4  -> llistarEscola.llistarTotes();
                    case 5  -> llistarSector.llistarUn();
                    case 6  -> llistarSector.llistarTots();
                    case 7  -> llistarVies.llistarUna();
                    case 8  -> llistarVies.llistarTotes();
                    // Crides als mètodes de lògica avançada amb consultes parametritzades.
                    case 9  -> llistarVies.viesDisponiblesEscola();
                    case 10 -> llistarVies.cercarPerRangGrau();
                    case 11 -> llistarVies.cercarPerEstat();
                    case 12 -> llistarEscola.llistarAmbRestriccions();
                    case 13 -> llistarSector.llistarAmbMesDeXVies();
                    case 14 -> llistarEscalador.llistarPerNivell();
                    case 15 -> llistarVies.viesRecentmentAptes();
                    case 16 -> llistarVies.viesMesLlargues();
                    // Si s'introdueix un 0, es modifica la condició d'escapament per sortir del bucle 'while'.
                    case 0  -> tornar = true;
                    // Comportament per defecte si el número introduït no coincideix amb cap 'case'.
                    default -> System.out.println("Has d'introduir un numero entre 0-16.");
                }
            // Si a l'intentar analitzar la línia com un enter falla (ex. l'usuari ha escrit "hola"), es captura l'error.
            } catch (NumberFormatException e) {
                // S'avisa a l'usuari que ha fallat i el bucle tornarà a començar.
                System.out.println("Has d'introduir un numero.");
            }
        }
    }
}
