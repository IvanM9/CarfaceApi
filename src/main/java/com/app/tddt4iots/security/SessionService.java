package com.app.tddt4iots.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.usuariodto.JwtDto;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {
    @Autowired
    UsuarioDao usuario;

    @Autowired
    JwtTokenService jwtTokenService;

    public String login(String correo, String clave) {
        try {
            Usuario respuesta = usuario.findOneByCorreo(correo).orElseThrow();
            System.out.println(respuesta);
            if (respuesta == null)
                return null;
            BCrypt.Result verificacion = BCrypt.verifyer().verify(clave.toCharArray(), respuesta.getClave());
            System.out.println(verificacion.verified);
            if (!verificacion.verified)
                return null;
            JwtDto datosToken = new JwtDto();
            datosToken.setId(respuesta.getId());
            datosToken.setCorreo(respuesta.getCorreo());
            datosToken.setRol(respuesta.getRol());
            return jwtTokenService.generateToken(
                    datosToken
                    ,respuesta.getRol() == Rol.CHOFER);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
