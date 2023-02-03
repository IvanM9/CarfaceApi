package com.app.tddt4iots.apis;

import com.app.tddt4iots.dtos.vehiculodto.CreateVehiculoDto;
import com.app.tddt4iots.entities.Vehiculo;
import com.app.tddt4iots.dao.VehiculoDao;
import com.app.tddt4iots.service.VehiculoService.VehiculoServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehiculo")
@CrossOrigin(origins = "*")
public class VehiculoApi {

    @Autowired
    private VehiculoDao vehiculoDAO;

    @Autowired
    private VehiculoServiceImplement vehiculoServiceImplement;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getVehiculo() {
        List<Vehiculo> listVehiculo = vehiculoDAO.findAll();
        return ResponseEntity.ok(listVehiculo);
    }

    @PostMapping("chofer/{id}")
    public ResponseEntity<?> insertVehiculo(@RequestBody CreateVehiculoDto vehiculo, @PathVariable("id") Long id) {
        Optional<Vehiculo> vehiculo1 = vehiculoServiceImplement.addVehiculo(id, vehiculo);
        return new ResponseEntity<>(vehiculo1.get(), vehiculo1.isEmpty() ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Vehiculo> updateVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo upVehiculo = vehiculoDAO.save(vehiculo);
        if (upVehiculo != null) {
            return ResponseEntity.ok(upVehiculo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Vehiculo> deletePersons(@PathVariable("id") Long id) {
        vehiculoDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
