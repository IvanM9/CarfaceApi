package com.app.tddt4iots.apis;

import com.app.tddt4iots.dtos.dispositivodto.CreateDispositivoDto;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.security.JwtTokenService;
import com.app.tddt4iots.service.DispositivoService.DispositivoServiceImplement;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dispositivo")
public class DispositivoApi {
    @Autowired
    DispositivoServiceImplement dispositivoService;
    @Autowired
    JwtTokenService jwtTokenService;
    ArrayList<Rol> rol = new ArrayList<>();

    @PostMapping("/comparar")
    public ResponseEntity<?> compareFace(@RequestPart(value = "files") MultipartFile file, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.DISPOSITIVO);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Boolean compare = dispositivoService.compareFace(file);
        return new ResponseEntity<>(compare ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<?> addDispositivo(@RequestBody CreateDispositivoDto dispositivoDto, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Usuario newDispositivo = dispositivoService.addDispositivo(dispositivoDto);
        return new ResponseEntity<>(newDispositivo, newDispositivo != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteDispositivo(@PathVariable("id") Long id, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Boolean eliminado = dispositivoService.deleteDispositivo(id);
        return new ResponseEntity<>("Eliminado: " + eliminado, eliminado ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateDispositivo(@PathVariable("id") Long id, @RequestHeader String Authorization, @RequestBody CreateDispositivoDto dispositivoDto) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        Boolean modificado = dispositivoService.updateDispositivo(dispositivoDto, id);
        return new ResponseEntity<>("Modificado: " + modificado, modificado ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllDispositivos(@RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.ADMINISTRADOR);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        List<JSONObject> dispositivos = dispositivoService.getAllDipositivos();
        return new ResponseEntity<>(dispositivos, HttpStatus.OK);
    }
}
