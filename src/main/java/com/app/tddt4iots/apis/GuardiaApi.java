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
@CrossOrigin(origins = "*")

public class GuardiaApi {

    @Autowired
    private GuardiaDao guardiaDAO;
    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    private GuardiaServiceImplement servicio;

    ArrayList rol = new ArrayList<Rol>();

    @GetMapping
    public ResponseEntity<List<Guardia>> getGuardia() {
        List<Guardia> listGuardia = guardiaDAO.findAll();
        return ResponseEntity.ok(listGuardia);
    }

    @PostMapping
    public ResponseEntity<?> insertGuardia(@RequestBody CreateGuardiaDto guardia, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null) {
            JSONObject error = new JSONObject();
            error.put("Mensaje", "No autorizado");
            return new ResponseEntity<>(error,
                    HttpStatus.FORBIDDEN);
        }
        Usuario newGuardia = servicio.saveGuardia(guardia);
        return ResponseEntity.ok(newGuardia);
    }

    @PutMapping
    public ResponseEntity<Guardia> updateGuardia(@RequestBody Guardia guardia) {
        Guardia upGuardia = guardiaDAO.save(guardia);
        if (upGuardia != null) {
            return ResponseEntity.ok(upGuardia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Guardia> deletePersons(@PathVariable("id") Long id) {
        guardiaDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }


}
