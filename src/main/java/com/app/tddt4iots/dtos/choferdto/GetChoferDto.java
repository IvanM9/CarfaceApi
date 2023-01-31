package com.app.tddt4iots.dtos.choferdto;

import com.app.tddt4iots.dtos.usuariodto.GetUsuarioDto;
import com.app.tddt4iots.enums.TipoLicencia;

import java.util.Date;

public class GetChoferDto extends GetUsuarioDto {
    public TipoLicencia licencia;

    public GetChoferDto(Long id, String nombre,
                        String apellido,
                        String ci,
                        String correo,
                        String Telefono,
                        String Direccion,
                        Date fechacreacion,
                        Date fechamodificacion,
                        TipoLicencia licencia) {
        this.licencia = licencia;
        this.nombre = nombre;
        this.fechacreacion = fechacreacion;
        this.apellido = apellido;
        this.ci = ci;
        this.direccion = Direccion;
        this.telefono = Telefono;
        this.fechamodificacion = fechamodificacion;
        this.correo = correo;
        this.id = id;
    }
}
