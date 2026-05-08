package Model.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static HikariDataSource dataSource = null;

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

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setPoolName("PillamPool");

            dataSource = new HikariDataSource(config);
            System.out.println("Pool Hikari inicialitzat (" + tipus + ").");

        } catch (Exception e) {
            throw new RuntimeException("Error inicialitzant la connexió: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) inicialitzar();
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool Hikari tancat.");
        }
    }
}