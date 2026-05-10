// Defineix el paquet on s'ubica la classe, corresponent a la capa de presentació (Vista).
package Vista;
// Importa la classe Scanner per permetre la lectura de dades introduïdes per teclat.
import java.util.Scanner;

// Importa tots els controladors necessaris per gestionar la creació de les diferents entitats.
import Controller.Escalador.*;
import Controller.Escola.*;
import Controller.Sector.*;
import Controller.Vies.*;

// Declaració de la classe que gestiona el submenú per crear nous registres.
public class menuCrear {
    // Mètode estàtic que mostra el menú per pantalla i processa la tria de l'usuari.
    public static void menu(){

        // Inicialitza un objecte Scanner per llegir l'entrada estàndard (consola).
        Scanner sc = new Scanner(System.in);
        // Mostra el títol del menú i les diferents opcions de creació disponibles.
        System.out.println("---------------CREAR---------------");
        System.out.println("1. ESCALADOR");
        System.out.println("2. ESCOLA");
        System.out.println("3. SECTOR");
        System.out.println("4. VIES");
        System.out.println("0. SORTIR");
        System.out.println("INTRODUEIX UNA DE LES OPCIONS");
        
        // Inici del bloc try-catch per gestionar possibles errors durant l'execució.
        try {
            // Llegeix el següent nombre enter introduït per l'usuari i el guarda a la variable 'op'.
            int op = sc.nextInt();

        // Avalua l'opció triada per executar l'acció corresponent (usant l'estructura clàssica d'switch amb 'break').
        switch (op) {
            case 1:
                // Crida al mètode del controlador per crear un nou escalador.
                crearEscalador.crearesc();
                // Surt de l'estructura switch.
                break;
            case 2:
                // Crida al mètode del controlador per crear una nova escola.
                crearEscola.crearEsco();
                break;
            case 3:
                // Crida al mètode del controlador per crear un nou sector.
                crearSector.crearSect();
                break;
            case 4:
                // Crida al submenú específic per crear vies d'escalada, ja que en té de diferents tipus.
                menuVies.menu();
                break;
            case 0:
                // Opció per sortir; no executa res i el break finalitza el switch, permetent tornar enrere.
                break;
            default:
                // Missatge d'avís si l'usuari introdueix un nombre fora del rang esperat.
                System.out.println("HAS D'INTRODUÏR UN NÚMERO ENTRE 0-4.");

        }
        // Captura genèrica per si salta alguna excepció (ex: posar lletres en comptes de números amb 'nextInt') i en mostra el motiu.
        }catch(Exception e){System.out.println("Error: "+ e.getMessage());}

    }
}
