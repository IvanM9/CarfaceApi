package com.app.tddt4iots.apis;

import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.security.JwtTokenService;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminApi {
    ArrayList<Rol> rol = new ArrayList<>();

    public AdminApi() {
        rol.add(Rol.ADMINISTRADOR);
    }

    @Autowired
    UsuarioServiceImplement usuarioServiceImplement;
    @Autowired
    JwtTokenService jwtTokenService;

    @GetMapping("/usuario/id{id}")
    public ResponseEntity<?> getUserId(@RequestHeader String Authorization, @RequestParam("id") Long id) {
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>("Usuario no autorizado", HttpStatus.FORBIDDEN);
        JSONObject usuario = usuarioServiceImplement.getUsuarioById(id);
        return new ResponseEntity<>(usuario, usuario != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    @GetMapping("/usuario/email{correo}")
    public ResponseEntity<?> getChofer(@RequestParam("correo") String correo, @RequestHeader String Authorization) {
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        JSONObject chofer = usuarioServiceImplement.getUsuarioByEmail(correo);
        return new ResponseEntity<>(chofer, chofer == null || chofer.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
