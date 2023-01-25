package com.app.tddt4iots.apis;

import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.dao.ChoferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chofer")
public class ChoferApi {

    @Autowired
    private ChoferDao choferDAO;

    @GetMapping
    public ResponseEntity<List<Chofer>> getChofer() {
        List<Chofer> listChofer = choferDAO.findAll();
        return ResponseEntity.ok(listChofer);
    }

    @PostMapping
    public ResponseEntity<Chofer> insertChofer(@RequestBody Chofer chofer) {
        Chofer newChofer = choferDAO.save(chofer);
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
