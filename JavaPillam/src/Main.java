import Model.DAO.*;
import Model.DAO.Clases.ActualitzarEstatsDAO;
import Model.DAO.Clases.*;
import Vista.*;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        try {
            DBConnection.getConnection();
            ActualitzarEstatsDAO.comprovarIActualitzar(); // <- aquí
            menuPrincipal menu = new menuPrincipal();
            menu.mostrar();
        } catch (Exception e) {
            System.out.println("Error conectant a la BDD: " + e.getMessage());
        } finally {
            DBConnection.closePool();
        }
    }
}