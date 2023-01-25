package com.app.tddt4iots.apis;

import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioApi {

    @Autowired
    private UsuarioDao usuarioDAO;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        List<Usuario> listUsuario = usuarioDAO.findAll();
        return ResponseEntity.ok(listUsuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> insertUsuario(@RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioDAO.save(usuario);
        return ResponseEntity.ok(newUsuario);
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario) {
        Usuario upUsuario = usuarioDAO.save(usuario);
        if (upUsuario != null) {
            return ResponseEntity.ok(upUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Usuario> deletePersons(@PathVariable("id") Long id) {
        usuarioDAO.deleteById(id);
        return ResponseEntity.ok(null);
    }

}
