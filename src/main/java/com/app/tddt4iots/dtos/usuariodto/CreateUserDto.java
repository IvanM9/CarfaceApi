package com.app.tddt4iots.dtos.usuariodto;

import lombok.Data;

@Data
public class CreateUserDto {
    public String ci;
    public String nombre;
    public String apellido;
    public String correo;
    public String clave;
}
