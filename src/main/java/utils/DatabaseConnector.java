package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String hostName;
    private final String userName;
    private final String password;
    private final String databaseName;
    private final int port;

    public DatabaseConnector(String hostName, String userName, String password, String databaseName, int port) {
        this.hostName = hostName;
        this.userName = userName;
        this.password = password;
        this.databaseName = databaseName;
        this.port = port;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер базы данных не был найден");
            System.exit(-1);
        }
        return DriverManager.getConnection("jdbc:postgresql://" +
                hostName + ":" + port + "/" + databaseName, userName, password);
    }
}
