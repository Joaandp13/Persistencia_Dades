// Importa les interfícies i mètodes de la capa d'accés a dades (DAO).
import Model.DAO.*;
// Importa específicament la classe encarregada de la lògica de manteniment d'estats a la base de dades.
import Model.DAO.Clases.ActualitzarEstatsDAO;
// Importa la resta de classes del paquet de dades (implementacions DAO).
import Model.DAO.Clases.*;
// Importa les classes de la interfície d'usuari (Vista).
import Vista.*;
// Importa la classe d'excepcions de SQL per gestionar errors de la base de dades.
import java.sql.SQLException;

// Classe principal que serveix com a punt d'entrada de l'aplicació.
public class Main {
    // Mètode main que inicia l'execució del programa.
    public static void main(String[] args) {
        // Bloc try per gestionar el flux principal i possibles fallades de connexió.
        try {
            // Intenta establir la connexió inicial amb la base de dades mitjançant el pool de connexions.
            DBConnection.getConnection();
            
            // Executa un mètode de manteniment inicial per verificar i actualitzar estats automàticament al començar.
            ActualitzarEstatsDAO.comprovarIActualitzar();
            
            // Instancia l'objecte del menú principal de la capa de vista.
            menuPrincipal menu = new menuPrincipal();
            // Crida al mètode que inicia el bucle d'interacció amb l'usuari.
            menu.mostrar();
            
        // Captura qualsevol error general (com problemes de xarxa o configuració de la BDD).
        } catch (Exception e) {
            // Mostra un missatge informatiu amb el detall de l'error produït.
            System.out.println("Error conectant a la BDD: " + e.getMessage());
            
        // Bloc que s'executa sempre, tant si el programa acaba bé com si salta una excepció.
        } finally {
            // Tanca el pool de connexions (HikariCP) per alliberar els recursos del sistema de forma segura.
            DBConnection.closePool();
        }
    }
}
