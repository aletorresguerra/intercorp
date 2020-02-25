package com.intercorp.entrevista.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Data
public class Client {

    private String firstName;
    private String lastName;
    private Integer age;
    private Date birthDate;
}
