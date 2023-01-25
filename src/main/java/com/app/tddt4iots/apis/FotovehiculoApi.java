package com.app.tddt4iots.apis;

import com.app.tddt4iots.entities.Fotovehiculo;
import com.app.tddt4iots.dao.FotovehiculoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fotovehiculo")
public class FotovehiculoApi {

    @Autowired
    private FotovehiculoDao fotovehiculoDAO;

    @GetMapping
    public ResponseEntity<List<Fotovehiculo>> getFotovehiculo() {
        List<Fotovehiculo> listFotovehiculo = fotovehiculoDAO.findAll();
        return ResponseEntity.ok(listFotovehiculo);
    }

    @PostMapping
    public ResponseEntity<Fotovehiculo> insertFotovehiculo(@RequestBody Fotovehiculo fotovehiculo) {
        Fotovehiculo newFotovehiculo = fotovehiculoDAO.save(fotovehiculo);
        return ResponseEntity.ok(newFotovehiculo);
    }

    @PutMapping
    public ResponseEntity<Fotovehiculo> updateFotovehiculo(@RequestBody Fotovehiculo fotovehiculo) {
        Fotovehiculo upFotovehiculo = fotovehiculoDAO.save(fotovehiculo);
        if (upFotovehiculo != null) {
            return ResponseEntity.ok(upFotovehiculo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Fotovehiculo> deletePersons(@PathVariable("id") Long id) {
        fotovehiculoDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
