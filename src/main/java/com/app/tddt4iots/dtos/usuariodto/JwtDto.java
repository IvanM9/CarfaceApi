package com.app.tddt4iots.dtos.usuariodto;

import com.app.tddt4iots.enums.Rol;

public class JwtDto {
    private Long id;
    private String correo;
    private Rol rol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
