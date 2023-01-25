package com.app.tddt4iots.apis;

import com.app.tddt4iots.entities.Registro;
import com.app.tddt4iots.dao.RegistroDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registro")
public class RegistroApi {

    @Autowired
    private RegistroDao registroDAO;

    @GetMapping
    public ResponseEntity<List<Registro>> getRegistro() {
        List<Registro> listRegistro = registroDAO.findAll();
        return ResponseEntity.ok(listRegistro);
    }

    @PostMapping
    public ResponseEntity<Registro> insertRegistro(@RequestBody Registro registro) {
        Registro newRegistro = registroDAO.save(registro);
        return ResponseEntity.ok(newRegistro);
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
