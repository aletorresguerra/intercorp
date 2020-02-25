package com.intercorp.entrevista.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component("ConnectionDB")
public class ConnectionDB {

    protected static Logger logger = LoggerFactory.getLogger(ConnectionDB.class);

    private static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static String RDS_HOSTNAME = "aa7blrx05eubc9.cilc6plsmbzw.sa-east-1.rds.amazonaws.com";
    private static String RDS_DATABASE_NAME = "ebdb";
    private static String RDS_USERNAME = "entrevista";
    private static String RDS_PASSWORD = "entrevista";
    private static String RDS_PORT = "3306";

    public static Connection getRemoteConnection() {
        if (RDS_HOSTNAME != null) {
            try {
                Class.forName(MYSQL_DRIVER);
                String dbName = RDS_DATABASE_NAME;
                String userName = RDS_USERNAME;
                String password = RDS_PASSWORD;
                String hostname = RDS_HOSTNAME;
                String port = RDS_PORT;
                String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password + "&autoReconnect=true";
                logger.trace("Getting remote connection with connection string from environment variables.");
                Connection con = DriverManager.getConnection(jdbcUrl);
                logger.info("Remote connection successful.");
                return con;
            } catch (ClassNotFoundException e) {
                logger.warn(e.toString());
            } catch (SQLException e) {
                logger.warn(e.toString());
            }
        }
        logger.error("No se encuentra el hostname de la base de datos");
        return null;
    }
}
