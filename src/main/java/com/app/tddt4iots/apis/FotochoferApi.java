package com.app.tddt4iots.apis;

import com.app.tddt4iots.entities.Fotochofer;
import com.app.tddt4iots.dao.FotochoferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fotochofer")
public class FotochoferApi {

    @Autowired
    private FotochoferDao fotochoferDAO;

    @GetMapping
    public ResponseEntity<List<Fotochofer>> getFotochofer() {
        List<Fotochofer> listFotochofer = fotochoferDAO.findAll();
        return ResponseEntity.ok(listFotochofer);
    }

    @PostMapping
    public ResponseEntity<Fotochofer> insertFotochofer(@RequestBody Fotochofer fotochofer) {
        Fotochofer newFotochofer = fotochoferDAO.save(fotochofer);
        return ResponseEntity.ok(newFotochofer);
    }

    @PutMapping
    public ResponseEntity<Fotochofer> updateFotochofer(@RequestBody Fotochofer fotochofer) {
        Fotochofer upFotochofer = fotochoferDAO.save(fotochofer);
        if (upFotochofer != null) {
            return ResponseEntity.ok(upFotochofer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Fotochofer> deletePersons(@PathVariable("id") Long id) {
        fotochoferDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
