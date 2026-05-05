import Model.DAO.*;
import Vista.*;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {

                try {
                    // Fuerza la conexión al arrancar
                    DBConnection.getConnection();

                    // Lanzas el menú principal
                    menuPrincipal menu = new menuPrincipal();
                    menu.mostrar();

                } catch (SQLException e) {
                    System.out.println("Error conectant a la BDD: " + e.getMessage());
                } finally {
                    // Se ejecuta siempre, tanto si hay error como si no
                    try {
                        DBConnection.closeConnection();
                    } catch (SQLException e) {
                        System.out.println("Error tancant la connexió: " + e.getMessage());
                    }
                }
            }
        }


