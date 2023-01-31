package com.app.tddt4iots.dtos.usuariodto;

import lombok.Data;

@Data
public class PutUserDto {
    public String nombre;
    public String apellido;
    public String direccion;
    public String telefono;
}
