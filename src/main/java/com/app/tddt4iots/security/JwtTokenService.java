package com.app.tddt4iots.security;

import com.app.tddt4iots.dtos.usuariodto.JwtDto;
import com.app.tddt4iots.enums.Rol;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;


@Service
public class JwtTokenService {
    private final Algorithm hmac512;
    private final JWTVerifier verifier;

    public JwtTokenService(@Value("${jwt.secret}") final String secret) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
    }

    public String generateToken(final JwtDto userDetails, Boolean expires) {
        String token = "";

        if (expires) {
            token = JWT.create()
                    .withClaim("id", userDetails.getId())
                    .withClaim("correo", userDetails.getCorreo())
                    .withClaim("rol", userDetails.getRol().name())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1200000))
                    .sign(this.hmac512);
        } else {
            token = JWT.create()
                    .withClaim("id", userDetails.getId())
                    .withClaim("correo", userDetails.getCorreo())
                    .withClaim("rol", userDetails.getRol().name())
                    .sign(this.hmac512);
        }
        return token;
    }


    public JwtDto validateTokenAndGetDatas(final String header) {
        try {
            String token = getOnlyToken(header);
            if(token==null)
                return null;
            Map<String, Claim> datos =  verifier.verify(token).getClaims();
            JwtDto datosToken = new JwtDto();
            datosToken.setId(datos.get("id").asLong());
            datosToken.setCorreo(String.valueOf(datos.get("correo")));
            datosToken.setRol(Rol.valueOf(datos.get("rol").asString()));
            return datosToken;
        } catch (final JWTVerificationException verificationEx) {
            System.out.println("token invalid: " + verificationEx.getMessage());
            return null;
        }
    }
    public JwtDto validateTokenAndGetDatas(final String header, ArrayList<Rol> roles) {
        JwtDto datos = validateTokenAndGetDatas(header);
        if(datos == null || !verifyRoles(roles, datos))
            return null;
        return datos;
    }


    private String getOnlyToken(String header){
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7);
    }

    public Boolean headerWithToken(String header){
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }
        return true;
    }

    private Boolean verifyRoles(ArrayList<Rol> roles, JwtDto token){
        return roles.contains(token.getRol());
    }

}
