package com.app.tddt4iots.dtos.guardiadto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class CreateGuardiaDto {
    private String compania;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String ci;

    private String nombre;
    private String apellido;
    private String correo;
    private String clave;

}
