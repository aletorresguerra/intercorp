package com.intercorp.entrevista.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component("ConnectionDB")
public class ConnectionDB {

    protected static Logger logger = LoggerFactory.getLogger(ConnectionDB.class);

    @Value("${RDS_HOSTNAME}")
    private static String RDS_HOSTNAME;

    public static Connection getRemoteConnection() {
        logger.debug(RDS_HOSTNAME);
        if ("aa7blrx05eubc9.cilc6plsmbzw.sa-east-1.rds.amazonaws.com" != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String dbName = "ebdb";
                String userName = "entrevista";
                String password = "entrevista";
                String hostname = "aa7blrx05eubc9.cilc6plsmbzw.sa-east-1.rds.amazonaws.com";
                String port = "3306";
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
        return null;
    }
}
