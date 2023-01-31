package com.app.tddt4iots.apis;

import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.choferdto.GetChoferDto;
import com.app.tddt4iots.dtos.choferdto.PutChoferDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.security.JwtTokenService;
import com.app.tddt4iots.service.ChoferService.ChoferServiceImplement;

import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import jakarta.servlet.annotation.HttpConstraint;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chofer")
@CrossOrigin(origins = "*")
public class ChoferApi {

    @Autowired
    private ChoferDao choferDAO;
    @Autowired
    private UsuarioServiceImplement usuarioServiceImplement;
    @Autowired
    ChoferServiceImplement servicio;

    @Autowired
    JwtTokenService jwtTokenService;

    private ArrayList<Rol> rol = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> getChoferes(@RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>("Usuario no autorizado", HttpStatus.FORBIDDEN);
        List<GetChoferDto> listChofer = servicio.getChoferes();
        return ResponseEntity.ok(listChofer);
    }

    @GetMapping(value = "{correo}")
    public ResponseEntity<?> getChofer(@PathVariable("correo") String correo) {
        JSONObject chofer = usuarioServiceImplement.getUsuarioByEmail(correo);
        return new ResponseEntity<>(chofer, chofer == null || chofer.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertChofer(@RequestBody CreateChoferDto chofer) {
        Usuario newChofer = servicio.saveChofer(chofer);
        return ResponseEntity.ok(newChofer);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Chofer> updateChofer(@RequestBody PutChoferDto chofer, @PathVariable("id") Long id) {
        Chofer upChofer = servicio.editChofer(chofer, id);
        if (upChofer != null) {
            return ResponseEntity.ok(upChofer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Chofer> deletePersons(@PathVariable("id") Long id) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        rol.add(Rol.CHOFER);
        choferDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
