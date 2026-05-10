package Model.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestiona el pool de connexions a la BDD mitjançant HikariCP.
 * La configuració (tipus de BD, URL, credencials) es llegeix de db.properties.
 * Suporta MySQL i PostgreSQL — canvia db.type al fitxer de propietats.
 */
public class DBConnection {

    private static HikariDataSource dataSource = null;

    // Singleton — ningú instancia aquesta classe directament
    private DBConnection() {}

    private static void inicialitzar() {
        try {
            Properties props = new Properties();
            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null)
                throw new RuntimeException("No s'ha trobat db.properties al classpath");

            props.load(input);
            String tipus = props.getProperty("db.type", "mysql");

            HikariConfig config = new HikariConfig();

            if (tipus.equals("postgres")) {
                config.setDriverClassName("org.postgresql.Driver");
                config.setJdbcUrl(props.getProperty("postgres.url"));
                config.setUsername(props.getProperty("postgres.user"));
                config.setPassword(props.getProperty("postgres.password"));
            } else {
                config.setDriverClassName("com.mysql.cj.jdbc.Driver");
                config.setJdbcUrl(props.getProperty("mysql.url"));
                config.setUsername(props.getProperty("mysql.user"));
                config.setPassword(props.getProperty("mysql.password"));
            }

            // 10 connexions màxim, mínim 2 en standby
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000);  // 30s per obtenir connexió
            config.setIdleTimeout(600000);        // 10min fins tancar connexió inactiva
            config.setPoolName("PillamPool");

            dataSource = new HikariDataSource(config);
            System.out.println("Pool Hikari inicialitzat (" + tipus + ").");

        } catch (Exception e) {
            throw new RuntimeException("Error inicialitzant la connexió: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna una connexió del pool. Si el pool no existeix, l'inicialitza.
     * La connexió s'ha de tancar (try-with-resources) per tornar-la al pool.
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) inicialitzar();
        return dataSource.getConnection();
    }

    /** Tanca el pool al finalitzar l'aplicació. Cridar des del main. */
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool Hikari tancat.");
        }
    }
}
