package com.app.tddt4iots.apis;

import com.app.tddt4iots.dtos.usuariodto.JwtDto;
import com.app.tddt4iots.dtos.vehiculodto.CreateVehiculoDto;
import com.app.tddt4iots.entities.Vehiculo;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.security.JwtTokenService;
import com.app.tddt4iots.dao.VehiculoDao;
import com.app.tddt4iots.service.VehiculoService.VehiculoServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoApi {

    @Autowired
    private VehiculoDao vehiculoDAO;

    @Autowired
    private VehiculoServiceImplement vehiculoServiceImplement;

    @Autowired
    private JwtTokenService jwtTokenService;

    private ArrayList<Rol> rol = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getVehiculo() {
        List<Vehiculo> listVehiculo = vehiculoDAO.findAll();
        return ResponseEntity.ok(listVehiculo);
    }

    @PostMapping
    public ResponseEntity<?> insertVehiculo(@RequestBody CreateVehiculoDto vehiculo, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.CHOFER);
        JwtDto user = jwtTokenService.validateTokenAndGetDatas(Authorization, rol);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Vehiculo> vehiculo1 = vehiculoServiceImplement.addVehiculo(user.getId(), vehiculo);
        return new ResponseEntity<>(vehiculo1, vehiculo1.isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateVehiculo(@RequestBody CreateVehiculoDto vehiculo, @PathVariable("id") Long id, @RequestHeader String Authorization) {
        rol.clear();
        rol.add(Rol.CHOFER);
        rol.add(Rol.ADMINISTRADOR);
        JwtDto usuario = jwtTokenService.validateTokenAndGetDatas(Authorization, rol);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Vehiculo> vehiculo1 = vehiculoServiceImplement.updateVehiculo(id, vehiculo, usuario.getRol() == Rol.CHOFER ? usuario.getId() : null);
        return new ResponseEntity<>(vehiculo1, !vehiculo1.isEmpty() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Vehiculo> deleteVehiculo(@PathVariable("id") Long id) {
        vehiculoDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/fotos/{id_vehiculo}")
    public ResponseEntity<?> uploadPhotos(@RequestPart(value = "files") MultipartFile[] files, @RequestHeader String Authorization, @PathVariable("id_vehiculo") Long id) {
        rol.clear();
        rol.add(Rol.CHOFER);
        JwtDto user = jwtTokenService.validateTokenAndGetDatas(Authorization, rol);
        if (jwtTokenService.validateTokenAndGetDatas(Authorization, rol) == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Boolean respuesta = vehiculoServiceImplement.uploadPhoto(files, user.getId(), id);
        return new ResponseEntity<>(respuesta ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
