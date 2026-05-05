package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String url      = "jdbc:mysql://localhost:3306/Pillam";
    private static final String user     = "root";
    private static final String password = "pirineus";


    private static Connection connection = null;

    // Constructor privado: nadie puede hacer "new DBConnection()" desde fuera
    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        // Si no existe o se ha cerrado/caído, la crea de nuevo
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexió establerta amb la BDD.");
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connexió tancada.");
        }
    }
}