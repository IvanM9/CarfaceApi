package com.app.tddt4iots.apis;

import com.app.tddt4iots.dtos.usuariodto.JwtDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Guardia;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.security.JwtTokenService;
import com.app.tddt4iots.service.GuardiaService.GuardiaServiceImplement;
import com.app.tddt4iots.dao.GuardiaDao;
import com.app.tddt4iots.dtos.guardiadto.CreateGuardiaDto;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guardia")

public class GuardiaApi {

    @Autowired
    private GuardiaDao guardiaDAO;
    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    private GuardiaServiceImplement servicio;

    ArrayList rol = new ArrayList<Rol>();

    @GetMapping("all")
    public ResponseEntity<?> getGuardias(@RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null) {
            return new ResponseEntity<>(JSONObject.toString("Mensaje", "No autorizado"),
                    HttpStatus.FORBIDDEN);
        }
        List<JSONObject> listGuardia = servicio.getAllGuardias();
        return new ResponseEntity<>(listGuardia, HttpStatus.OK);
    }

    @GetMapping("{placa}")
    public ResponseEntity<?> escanearPlaca(@PathVariable("placa") String placa, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.GUARDIA);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null) {
            return new ResponseEntity<>(JSONObject.toString("Mensaje", "No autorizado"),
                    HttpStatus.FORBIDDEN);
        }
        JSONObject datos = servicio.escanearPlaca(placa);
        return new ResponseEntity<>(datos, datos != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<?> insertGuardia(@RequestBody CreateGuardiaDto guardia, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null) {
            return new ResponseEntity<>(JSONObject.toString("Mensaje", "No autorizado"),
                    HttpStatus.FORBIDDEN);
        }
        Usuario newGuardia = servicio.saveGuardia(guardia);
        return ResponseEntity.ok(newGuardia);
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<?> updateGuardia(@RequestBody CreateGuardiaDto guardia, @PathVariable("id") Long id) {
        return new ResponseEntity<>(servicio.updateGuardia(guardia, id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "{id}/{estado}")
    public ResponseEntity<?> updateEstado(@PathVariable("id") Long id, @PathVariable("estado") Boolean estado) {
        return new ResponseEntity<>(servicio.updateEstado(id, estado) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


}
