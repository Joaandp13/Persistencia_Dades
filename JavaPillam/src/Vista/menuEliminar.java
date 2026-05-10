// Defineix el paquet on s'ubica la classe, corresponent a la capa de presentació o interfície (Vista).
package Vista;

// Importa els controladors encarregats de gestionar l'eliminació de les diferents entitats de la base de dades.
import Controller.Escalador.eliminarEscalador;
import Controller.Escola.eliminarEscola;
import Controller.Sector.eliminarSector;
import Controller.Vies.eliminarVies;
// Importa la classe Scanner de Java per poder llegir l'entrada de text per consola.
import java.util.Scanner;

// Declaració de la classe que conté el submenú de l'opció d'eliminar registres.
public class menuEliminar {
    // Mètode estàtic que mostra el menú i processa la decisió de l'usuari.
    public static void menu() {
        // Es crea una instància de Scanner associada a l'entrada estàndard (teclat).
        Scanner sc = new Scanner(System.in);
        // Mostra les opcions disponibles per esborrar dades del sistema.
        System.out.println("---------------ELIMINAR---------------");
        System.out.println("1. ESCALADOR");
        System.out.println("2. ESCOLA");
        System.out.println("3. SECTOR");
        System.out.println("4. VIES");
        System.out.println("0. TORNAR");
        System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");
        
        // Bloc try-catch per controlar els errors potencials durant la introducció de dades.
        try {
            // Llegeix l'entrada sencera, la transforma a número enter i la desa a la variable 'op'.
            int op = Integer.parseInt(sc.nextLine());
            // Avalua el valor introduït per executar l'acció corresponent.
            switch (op) {
                // Del cas 1 al 4: Crida el mètode específic del controlador per eliminar l'entitat seleccionada.
                case 1 -> eliminarEscalador.eliminarEsc();
                case 2 -> eliminarEscola.eliminarEsco();
                case 3 -> eliminarSector.eliminarSect();
                case 4 -> eliminarVies.eliminarVie();
                // Si l'opció és 0, no fa res i permet sortir de l'estructura switch per tornar al menú anterior.
                case 0 -> {}
                // Missatge d'avís si l'usuari introdueix un número fora del rang vàlid.
                default -> System.out.println("HAS D'INTRODUIR UN NUMERO ENTRE 0-4.");
            }
        // Captura l'error que es dona si l'usuari escriu lletres o símbols en comptes d'un número enter.
        } catch (NumberFormatException e) {
            System.out.println("Has d'introduir un numero.");
        // Captura qualsevol altra excepció general que pugui ocórrer, evitant que el programa es pengi.
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
