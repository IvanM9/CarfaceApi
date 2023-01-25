package com.app.tddt4iots.dtos.guardiadto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CreateGuardiaDto {
    private String compania;
    private String fecha_inicio;
    private String fecha_fin;
    private String ci;

    private String nombre;
    private String apellido;
    private String correo;
    private String clave;

    public String getCompania() {
        return compania;
    }

    public String getClave() {
        return clave;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public String getCi() {
        return ci;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }
}
