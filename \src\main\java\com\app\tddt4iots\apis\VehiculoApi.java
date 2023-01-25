package com.app.tddt4iots.apis;

import com.app.tddt4iots.entities.Vehiculo;
import com.app.tddt4iots.dao.VehiculoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoApi {

    @Autowired
    private VehiculoDao vehiculoDAO;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getVehiculo() {
        List<Vehiculo> listVehiculo = vehiculoDAO.findAll();
        return ResponseEntity.ok(listVehiculo);
    }

    @PostMapping
    public ResponseEntity<Vehiculo> insertVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo newVehiculo = vehiculoDAO.save(vehiculo);
        return ResponseEntity.ok(newVehiculo);
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
