package com.intercorp.entrevista.Services;

import com.intercorp.entrevista.Model.Client;
import com.intercorp.entrevista.Model.ClientList;
import com.intercorp.entrevista.Model.KPIClients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServicesTest {

    public static final String TEST = "Test";
    public static final String TEST_LAST_NAME = "TestLastName";
    public static final int AGE = 20;
    public static final String QUERY = "select * from client";

    @InjectMocks
    private Client clientInput = mock(Client.class);

    @InjectMocks
    private ClientServices clientServicesMock = mock(ClientServices.class);

    @Test
    void insertClientSuccessful() throws Exception {
        final Date date = Mockito.mock(Date.class);

        when(clientInput.getAge()).thenReturn(AGE);
        when(clientInput.getFirstName()).thenReturn(TEST);
        when(clientInput.getLastName()).thenReturn(TEST_LAST_NAME);
        when(clientInput.getBirthDate()).thenReturn(date);
        Client client = ClientServices.insertClient(clientInput);

        assertNotNull(client);
        assertEquals(client.getAge(), AGE);
        assertEquals(client.getFirstName(), TEST);
        assertEquals(client.getLastName(), TEST_LAST_NAME);
    }

    @Test
    void obtainClientsEmptyList() throws Exception {
        List<ClientList> clientLists = clientServicesMock.obtainClients();

        assertNotNull(clientLists);
        assertEquals(clientLists.size(), 0);
    }

    @Test
    void getKpiClientsEmpty() throws Exception {
        KPIClients kpiClients = clientServicesMock.kpiClients();

        assertNull(kpiClients);
    }
}