// Defineix el paquet on s'ubica la classe, formant part de la capa de presentació (interfície d'usuari).
package Vista;

// Importa els controladors encarregats de la lògica de creació de cada tipus de via d'escalada.
import Controller.Vies.TipusVies.crearClassica;
import Controller.Vies.TipusVies.crearEsportiva;
import Controller.Vies.TipusVies.crearGel;
// Importa la classe Scanner nativa de Java per poder llegir l'entrada del teclat de l'usuari.
import java.util.Scanner;

// Declaració de la classe que gestiona el submenú de vies.
public class menuVies {
    
    // Mètode estàtic que executa el menú. A l'ésser estàtic, es pot cridar directament sense instanciar la classe 'menuVies'.
    public static void menu() {
        
        // Crea un objecte Scanner per llegir dades de l'entrada estàndard (System.in, és a dir, la consola).
        Scanner sc = new Scanner(System.in);
        
        // Mostra per pantalla el títol i les opcions del menú d'usuari.
        System.out.println("---------------VIES---------------");
        System.out.println("1. VIA ESPORTIVA");
        System.out.println("2. VIA CLASSICA");
        System.out.println("3. VIA DE GEL");
        System.out.println("0. TORNAR");
        System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");
        
        // Inicia un bloc de control d'errors per evitar que el programa peti si l'usuari no introdueix un nombre.
        try {
            
            // Llegeix tota la línia introduïda, la converteix a un nombre enter (int) i ho desa a la variable 'op'.
            int op = Integer.parseInt(sc.nextLine());
            
            // Estructura de control per decidir quina acció prendre en funció del valor d'op' (sintaxi moderna amb fletxes).
            switch (op) {
                
                // Si escriu un 1, crida la funció del controlador per gestionar les vies esportives.
                case 1 -> crearEsportiva.crearesportiva();
                
                // Si escriu un 2, crida la funció corresponent a les vies clàssiques.
                case 2 -> crearClassica.crearclassica();
                
                // Si escriu un 3, crida la funció per a les vies de gel.
                case 3 -> crearGel.creargel();
                
                // Si escriu un 0, no fa cap acció (surt de l'switch i acaba el mètode, retornant segurament a un menú superior).
                case 0 -> {}
                
                // Si ha posat un nombre que no és cap d'aquests (ex: un 8), l'avisa amb un missatge.
                default -> System.out.println("HAS D'INTRODUIR UN NUMERO ENTRE 0-3.");
            }
            
        // Captura l'excepció que es produeix si la conversió 'Integer.parseInt' falla (si ha ficat text en lloc de números).
        } catch (NumberFormatException e) {
            
            // Imprimeix l'error indicant a l'usuari com ho ha de fer.
            System.out.println("Has d'introduir un numero.");
        }
    }
}
