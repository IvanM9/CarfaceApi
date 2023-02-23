package com.app.tddt4iots.dtos.registrodto;

import com.app.tddt4iots.enums.Tipo;
import lombok.Data;
import com.app.tddt4iots.entities.Registro;;

@Data
public class CreateRegistroDto{
    Long id_chofer;
    Long id_vehiculo;
    Tipo tipo;
    String observaciones;
}
