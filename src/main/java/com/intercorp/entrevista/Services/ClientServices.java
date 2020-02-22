package com.intercorp.entrevista.Services;

import com.intercorp.entrevista.Config.ConnectionDB;
import com.intercorp.entrevista.Model.Client;
import com.intercorp.entrevista.Model.KPIClients;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientServices {

    static Connection connection = ConnectionDB.getRemoteConnection();
    static PreparedStatement preparedStatement;
    protected static Logger logger = LoggerFactory.getLogger(ClientServices.class);

    public static Client insertClient(Client client) throws Exception {
        String query = "insert into client (first_name,last_name,age,date) values (?,?,?,?)";
        return connectionDBPOST(client, query);
    }

    public List<Client> obtainClients() throws Exception {
        String query= "select * from client";
        return connectionDBPGET(query);
    }

    public KPIClients kpiClients() throws Exception {
        KPIClients kpiClients = new KPIClients();
        double varianza = 0, desviacion, prom = 0;

        String query= "select * from client";
        List<Client> clients = connectionDBPGET(query);
        for(int i=0;i<clients.size();i++){
            prom = prom + clients.get(i).getAge();
        }
        prom = prom/clients.size();

        for(int i=0;i<clients.size();i++){
            Double variacion = Math.pow(clients.get(i).getAge() - prom, 2f);
            varianza = varianza + variacion;
        }
        varianza = varianza / clients.size();
        desviacion = Math.sqrt(varianza);

        kpiClients.setPromedioEdad(prom);
        kpiClients.setDesviacion(desviacion);
        return kpiClients;
    }

    private static Client connectionDBPOST(Client client, String query) throws Exception {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.setDate(4, client.getBirthDate());

            preparedStatement.execute();

            connection.close();
            return client;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new SQLException(sqle.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getCause());
        }
    }

    private List<Client> connectionDBPGET(String query) throws Exception {
        try {
            List<Client> clients = new ArrayList<Client>();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(query);

            while (resultSet.next()) {
                Client client = new Client();
                client.setFirstName(resultSet.getString("first_name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setAge(resultSet.getInt("age"));
                client.setBirthDate(resultSet.getDate("date"));
                clients.add(client);
            }
            st.close();
            return clients;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new SQLException(sqle.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getCause());
        }
    }
}
