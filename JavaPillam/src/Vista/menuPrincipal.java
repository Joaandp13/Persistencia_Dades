// Defineix el paquet on es troba la classe, corresponent a la interfície d'usuari (Vista).
package Vista;

// Importa la classe Scanner per permetre la lectura de dades introduïdes per l'usuari.
import java.util.Scanner;

// Declaració de la classe principal que gestiona el menú d'inici de l'aplicació.
public class menuPrincipal {
    // Mètode públic que executa i mostra el menú a l'usuari.
    public void mostrar() {
        // Inicialitza el Scanner per llegir el text des de la consola (System.in).
        Scanner sc = new Scanner(System.in);
        // Variable booleana de control que mantindrà el menú en funcionament.
        boolean actiu = true;

        // Bucle que es repeteix contínuament fins que l'usuari decideixi sortir.
        while (actiu) {
            // Bloc d'instruccions per imprimir la capçalera i les opcions per pantalla.
            System.out.println("\n=======================================");
            System.out.println("       PILLAM Ltd. Co. - ESCALADA      ");
            System.out.println("=======================================");
            System.out.println("1. CREAR");
            System.out.println("2. MODIFICAR");
            System.out.println("3. LLISTAR / CONSULTAR");
            System.out.println("4. ELIMINAR");
            System.out.println("0. SORTIR");
            System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");

            // Inici del bloc try-catch per gestionar possibles errors en la introducció de dades.
            try {
                // Llegeix la línia escrita per l'usuari i la converteix a nombre enter.
                int op = Integer.parseInt(sc.nextLine());
                // Avalua l'opció numèrica per decidir cap a on anar.
                switch (op) {
                    // Crida als mètodes estàtics dels submenús segons la tria de l'usuari.
                    case 1 -> menuCrear.menu();
                    case 2 -> menuModificar.menu();
                    case 3 -> menuLlistar.menu();
                    case 4 -> menuEliminar.menu();
                    // Si tria l'opció 0, s'executa aquest bloc per tancar el menú.
                    case 0 -> {
                        System.out.println("SORTINT...");
                        // Canvia la variable a 'false' per trencar el bucle 'while'.
                        actiu = false;
                    }
                    // Si s'introdueix un número que no és cap de les opcions anteriors.
                    default -> System.out.println("Has d'introduir un numero entre 0-4.");
                }
            // Captura l'error específic si s'escriuen caràcters no numèrics (lletres, símbols).
            } catch (NumberFormatException e) {
                // Avisa a l'usuari de l'error perquè ho torni a intentar.
                System.out.println("Has d'introduir un numero.");
            }
        }
    }
}
