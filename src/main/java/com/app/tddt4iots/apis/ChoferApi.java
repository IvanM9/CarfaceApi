package com.app.tddt4iots.apis;

import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.choferdto.GetChoferDto;
import com.app.tddt4iots.dtos.choferdto.PutChoferDto;
import com.app.tddt4iots.dtos.usuariodto.JwtDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chofer")
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

    @GetMapping("all")
    public ResponseEntity<?> getChoferes(@RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>("Usuario no autorizado", HttpStatus.FORBIDDEN);
        List<GetChoferDto> listChofer = servicio.getChoferes();
        return ResponseEntity.ok(listChofer);
    }



    @GetMapping
    public ResponseEntity<?> getChoferById(@RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.CHOFER);
        JwtDto chofer = jwtTokenService.validateTokenAndGetDatas(Authorization, rol);
        if (chofer == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        JSONObject chofer2 = usuarioServiceImplement.getUsuarioByEmail(chofer.getCorreo());
        return new ResponseEntity<>(chofer2, chofer2 == null || chofer2.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insertChofer(@RequestBody CreateChoferDto chofer) {
        Usuario newChofer = servicio.saveChofer(chofer);
        return new ResponseEntity<>(newChofer, newChofer != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Chofer> updateChofer(@RequestBody PutChoferDto chofer, @PathVariable("id") Long id, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.CHOFER);
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        Chofer upChofer = servicio.editChofer(chofer, id);
        return new ResponseEntity<>(upChofer, upChofer != null ? HttpStatus.OK : HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Chofer> deletePersons(@PathVariable("id") Long id, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        rol.add(Rol.CHOFER);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        choferDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/fotos")
    public ResponseEntity<?> uploadPhotos(@RequestPart(value = "files") MultipartFile[] files, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.CHOFER);
        JwtDto user = jwtTokenService.validateTokenAndGetDatas(Authorization, rol);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Boolean respuesta = servicio.uploadPhoto(files, user.getId());
        return new ResponseEntity<>(respuesta ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
