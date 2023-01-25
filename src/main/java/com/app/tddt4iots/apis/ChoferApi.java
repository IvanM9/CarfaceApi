package com.app.tddt4iots.apis;

import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.security.JwtTokenService;
import com.app.tddt4iots.service.ChoferService.ChoferServiceImplement;
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
    ChoferServiceImplement servicio;

    @Autowired
    JwtTokenService jwtTokenService;

    @GetMapping
    public ResponseEntity<?> getChofer(@RequestHeader String Authorization) {
        ArrayList<Rol> rol = new ArrayList<>();
        rol.add(Rol.ADMINISTRADOR);
        if(jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>("Usuario no autorizado", HttpStatus.FORBIDDEN);
        List<Chofer> listChofer = choferDAO.findAll();
        return ResponseEntity.ok(listChofer);
    }

    @PostMapping
    public ResponseEntity<?> insertChofer(@RequestBody CreateChoferDto chofer) {
        Usuario newChofer = servicio.saveChofer(chofer);
        return ResponseEntity.ok(newChofer);
    }

    @PutMapping
    public ResponseEntity<Chofer> updateChofer(@RequestBody Chofer chofer) {
        Chofer upChofer = choferDAO.save(chofer);
        if (upChofer != null) {
            return ResponseEntity.ok(upChofer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Chofer> deletePersons(@PathVariable("id") Long id) {
        choferDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
