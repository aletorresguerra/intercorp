package com.intercorp.entrevista.Model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KPIClients {

    public Double promedioEdad;
    public Double desviacion;

    public Double getPromedioEdad() {
        return promedioEdad;
    }

    public void setPromedioEdad(Double promedioEdad) {
        this.promedioEdad = promedioEdad;
    }

    public Double getDesviacion() {
        return desviacion;
    }

    public void setDesviacion(Double desviacion) {
        this.desviacion = desviacion;
    }
}
