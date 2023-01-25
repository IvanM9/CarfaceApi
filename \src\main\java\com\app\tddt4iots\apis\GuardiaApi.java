package com.app.tddt4iots.apis;

import com.app.tddt4iots.entities.Guardia;
import com.app.tddt4iots.dao.GuardiaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guardia")
public class GuardiaApi {

    @Autowired
    private GuardiaDao guardiaDAO;

    @GetMapping
    public ResponseEntity<List<Guardia>> getGuardia() {
        List<Guardia> listGuardia = guardiaDAO.findAll();
        return ResponseEntity.ok(listGuardia);
    }

    @PostMapping
    public ResponseEntity<Guardia> insertGuardia(@RequestBody Guardia guardia) {
        Guardia newGuardia = guardiaDAO.save(guardia);
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
