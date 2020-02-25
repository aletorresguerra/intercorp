package com.intercorp.entrevista.Controller;

import com.intercorp.entrevista.Model.Client;
import com.intercorp.entrevista.Model.ClientList;
import com.intercorp.entrevista.Model.KPIClients;
import com.intercorp.entrevista.Services.ClientServices;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.intercorp.entrevista.Utils.ControllerUtils.isString;

@RestController
public class ClientController {

    protected static Logger logger = LoggerFactory.getLogger(ClientController.class);
    ClientServices clientServices = new ClientServices();

    @ApiOperation(value = "Create a new client", httpMethod = "POST")
    @RequestMapping(value = "/createClient", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Client newClient(@RequestBody Client client) throws Exception {
        if (client == null){
            logger.error("Request body cannot be empty");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Request body cannot be empty");
        }
            validateClient(client);
            manageDate(client);
            return ClientServices.insertClient(client);
    }

    @ApiOperation(value = "Return KPI of all clients", httpMethod = "GET")
    @RequestMapping(value = "/kpiClient", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public KPIClients kpiOfClients() throws Exception {
        return clientServices.kpiClients();
    }

    @ApiOperation(value = "Return all clients registered", httpMethod = "GET")
    @RequestMapping(value = "/listClient", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<ClientList> listClients() throws Exception {
        return clientServices.obtainClients();
    }

    private void validateClient(Client client) {
        if (!isString(client.getLastName()) || !isString(client.getLastName())) {
            logger.error("Error en el request enviado al servicio. Validar los datos enviados!!");
        }
    }

    private void manageDate(Client client) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmtTime = new Date(sdf.format(client.getBirthDate()));
        client.setBirthDate(new java.sql.Date(gmtTime.getTime()));
    }
}