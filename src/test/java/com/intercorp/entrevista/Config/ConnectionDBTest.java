package com.intercorp.entrevista.Config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConnectionDBTest {

    @Test
    public void connectionSuccessful() {
        Connection connection = ConnectionDB.getRemoteConnection();
        assertNotNull(connection);
    }
}