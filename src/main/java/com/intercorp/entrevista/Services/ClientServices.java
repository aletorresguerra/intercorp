package com.intercorp.entrevista.Services;

import com.intercorp.entrevista.Config.ConnectionDB;
import com.intercorp.entrevista.Model.Client;
import com.intercorp.entrevista.Model.ClientList;
import com.intercorp.entrevista.Model.KPIClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ClientServices {

    public static final String QUERY = "select * from client";
    @Autowired
    private static Connection connection = ConnectionDB.getRemoteConnection();
    @Autowired
    private static PreparedStatement preparedStatement;

    public static Client insertClient(Client client) throws Exception {
        String query = "insert into client (first_name,last_name,age,date) values (?,?,?,?)";
        return connectionDBPOST(client, query);
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

    public List<ClientList> obtainClients() throws Exception {
        List<Client> clients = connectionDBPGET(QUERY);
        List<ClientList> clientLists = new ArrayList<>();
        Integer lifespan = 75;
        for (int i = 0; i < clients.size(); i++) {
            ClientList clientList = new ClientList();
            clientList.setAge(clients.get(i).getAge());
            clientList.setBirthDate(clients.get(i).getBirthDate());
            clientList.setFirstName(clients.get(i).getFirstName());
            clientList.setLastName(clients.get(i).getLastName());
            clientList.setPossiblyDeath(possibleDeath(clients.get(i).getBirthDate(), lifespan));

            clientLists.add(clientList);
        }
        return clientLists;
    }

    public KPIClients kpiClients() throws Exception {
        KPIClients kpiClients = new KPIClients();
        double variance = 0, deviation, average = 0;

        List<Client> clients = connectionDBPGET(QUERY);
        if (clients.size() != 0) {
            average = getAverage(average, clients);
            deviation = getDeviation(variance, average, clients);

            kpiClients.setAverageAge(average);
            kpiClients.setDeviation(deviation);
        }
        return kpiClients;
    }

    private double getDeviation(double variance, double average, List<Client> clients) {
        double deviation;
        for (int i = 0; i < clients.size(); i++) {
            Double variation = Math.pow(clients.get(i).getAge() - average, 2f);
            variance += variation;
        }
        variance = variance / clients.size();
        deviation = Math.sqrt(variance);
        return deviation;
    }

    private double getAverage(double average, List<Client> clients) {
        for (int i = 0; i < clients.size(); i++) {
            average += clients.get(i).getAge();
        }
        average = average / clients.size();
        return average;
    }

    List<Client> connectionDBPGET(String query) throws Exception {
        try {
            List<Client> clients = new ArrayList<>();
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

    private Date possibleDeath(Date birthDate, Integer lifespan) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        calendar.add(Calendar.YEAR, lifespan);
        return new java.sql.Date((calendar.getTime()).getTime());
    }
}
