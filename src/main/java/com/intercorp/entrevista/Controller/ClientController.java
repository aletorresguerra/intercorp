package com.intercorp.entrevista.Controller;

import com.intercorp.entrevista.Exception.BadRequestException;
import com.intercorp.entrevista.Model.Client;
import com.intercorp.entrevista.Model.KPIClients;
import com.intercorp.entrevista.Services.ClientServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.intercorp.entrevista.Utils.ControllerUtils.isString;

@RestController
public class ClientController {

    ClientServices clientServices = new ClientServices();

    @RequestMapping(value = "/createClient", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Client newClient(@RequestBody Client client) throws Exception {
        validateClient(client);
        manageDate(client);
        return ClientServices.insertClient(client);
    }

    @RequestMapping(value = "/kpiClient", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public KPIClients kpiOfClients() throws Exception {
        return clientServices.kpiClients();
    }

    @RequestMapping(value = "/listClient", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Client> listClients() throws Exception {
        return clientServices.obtainClients();
    }

    private void validateClient(Client client) {
        if (!isString(client.getLastName()) || !isString(client.getLastName())) {
            throw new BadRequestException("Error en el request enviado al servicio. Validar los datos enviados!!");
        }
    }

    private void manageDate(Client client) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmtTime = new Date(sdf.format(client.getBirthDate()));
        client.setBirthDate(new java.sql.Date(gmtTime.getTime()));
    }
}