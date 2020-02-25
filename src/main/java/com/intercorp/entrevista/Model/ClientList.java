package com.intercorp.entrevista.Model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ClientList extends Client {

    private Date possiblyDeath;
}
