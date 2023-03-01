package com.app.tddt4iots.apis;

import com.app.tddt4iots.dtos.registrodto.CreateRegistroDto;
import com.app.tddt4iots.dtos.usuariodto.JwtDto;
import com.app.tddt4iots.entities.Registro;
import com.app.tddt4iots.dao.RegistroDao;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.security.JwtTokenService;
import com.app.tddt4iots.service.RegistroService.RegistroService;
import com.app.tddt4iots.service.RegistroService.RegistroServiceImplement;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registro")
public class RegistroApi {

    @Autowired
    private RegistroDao registroDAO;

    @Autowired
    private RegistroServiceImplement registroService;
    @Autowired
    JwtTokenService jwtTokenService;

    ArrayList<Rol> rols = new ArrayList<>();

    @GetMapping("/get_registros")
    public ResponseEntity<?> getRegistros() {
        List<JSONObject> listRegistro = registroService.getRegistros();
        return new ResponseEntity<>(listRegistro.toArray(), listRegistro != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistro(@PathVariable("id") Long id) {
        Registro registro = registroService.getRegistroId(id);
        return new ResponseEntity<>(registro, registro != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<?> insertRegistro(@RequestBody CreateRegistroDto registro, @RequestHeader String Authorization) {
        rols.clear();
        rols.add(Rol.GUARDIA);
        JwtDto guardia = jwtTokenService.validateTokenAndGetDatas(Authorization, rols);
        if ( guardia == null) {
            return new ResponseEntity<>("Usuario no autorizado", HttpStatus.FORBIDDEN);
        }
        Registro newRegistro = registroService.insertRegistro(registro, guardia.getId());
        return new ResponseEntity<>(newRegistro, newRegistro != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Registro> updateRegistro(@RequestBody Registro registro) {
        Registro upRegistro = registroDAO.save(registro);
        if (upRegistro != null) {
            return ResponseEntity.ok(upRegistro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Registro> deletePersons(@PathVariable("id") Long id) {
        registroDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
