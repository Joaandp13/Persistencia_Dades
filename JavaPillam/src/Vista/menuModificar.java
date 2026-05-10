// Defineix el paquet on s'ubica la classe, corresponent a la capa de presentació o interfície (Vista).
package Vista;

// Importa els controladors necessaris per gestionar la modificació de cadascuna de les entitats.
import Controller.Escalador.modificarEscalador;
import Controller.Escola.modificarEscola;
import Controller.Sector.modificarSector;
import Controller.Vies.modificarVies;
// Importa la classe Scanner per permetre la lectura de l'entrada per teclat.
import java.util.Scanner;

// Declaració de la classe que gestiona el submenú per modificar dades existents.
public class menuModificar {
    // Mètode estàtic que imprimeix el menú i processa la tria de l'usuari.
    public static void menu() {
        // Inicialitza l'objecte Scanner per capturar el que l'usuari escrigui a la consola.
        Scanner sc = new Scanner(System.in);
        // Mostra per pantalla el títol del menú i les opcions de modificació disponibles.
        System.out.println("---------------MODIFICAR---------------");
        System.out.println("1. ESCALADOR");
        System.out.println("2. ESCOLA");
        System.out.println("3. SECTOR");
        System.out.println("4. VIES");
        System.out.println("0. TORNAR");
        System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");
        
        // Inici del bloc de gestió d'excepcions per evitar interrupcions per errors d'entrada.
        try {
            // Llegeix la línia sencera del teclat, la converteix a enter (int) i la guarda a 'op'.
            int op = Integer.parseInt(sc.nextLine());
            // Estructura switch per executar una acció o una altra segons el número introduït.
            switch (op) {
                // Opcions de l'1 al 4: criden als respectius mètodes de modificació dels controladors.
                case 1 -> modificarEscalador.modificarEsc();
                case 2 -> modificarEscola.modificarEsco();
                case 3 -> modificarSector.modificarSect();
                case 4 -> modificarVies.modificarVie();
                // Opció 0: bloc buit, finalitza el mètode i retorna al menú anterior automàticament.
                case 0 -> {}
                // En cas de posar un número fora de les opcions (ex: 5 o 9), es mostra aquest avís.
                default -> System.out.println("HAS D'INTRODUIR UN NUMERO ENTRE 0-4.");
            }
        // Captura l'error específic que salta si s'intenta convertir text (lletres) a un número.
        } catch (NumberFormatException e) {
            // Missatge d'error informant a l'usuari del format correcte.
            System.out.println("Has d'introduir un numero.");
        // Captura genèrica per a qualsevol altre tipus d'excepció no prevista durant l'execució.
        } catch (Exception e) {
            // Mostra per pantalla la descripció del problema inesperat.
            System.out.println("Error: " + e.getMessage());
        }
    }
}
