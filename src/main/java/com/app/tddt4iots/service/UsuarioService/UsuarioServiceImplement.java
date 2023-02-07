package com.app.tddt4iots.service.UsuarioService;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.dtos.usuariodto.PutUserDto;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UsuarioServiceImplement implements UsuarioService {
    @Autowired
    UsuarioDao repository;


    @Override
    public ArrayList<Usuario> getUsuarios() {
        return (ArrayList<Usuario>) repository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(long id) {

        return repository.findById(id);
    }

    @Override
    public JSONObject getUsuarioByEmail(String correo) {
        JSONObject object = new JSONObject();
        try {
            Usuario usuario = repository.findOneByCorreo(correo).orElseThrow();
            object.put("nombre", usuario.getNombres());
            object.put("apellido", usuario.getApellidos());
            object.put("ci", usuario.getCedula());
            object.put("direccion",usuario.getDireccion());
            object.put("telefono", usuario.getTelefono());
            object.put("id", usuario.getId());
            switch (usuario.getRol()){
                case CHOFER -> {
                    object.put("chofer",usuario.getChofer());
                }
                case GUARDIA -> object.put("guardia", usuario.getGuardia());
            }
            return object;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public Boolean deleteUsuario(long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public Usuario editUsuario(Long id, PutUserDto usuario) {
        repository.updateNombresAndApellidosById(usuario.getNombre(), usuario.getApellido(), id);
        return repository.findById(id).orElseThrow();
    }


    public Usuario createUsuario(CreateUserDto usuario1, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setCedula(usuario1.ci);
        usuario.setCorreo(usuario1.correo);
        usuario.setClave(usuario1.clave);
        usuario.setNombres(usuario1.nombre);
        usuario.setApellidos(usuario1.apellido);
        usuario.setDireccion(usuario1.getDireccion());
        usuario.setTelefono(usuario1.getTelefono());
        usuario.setRol(rol);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        usuario.setFechacreacion(timestamp);
        usuario.setFechamodificacion(timestamp);
        usuario.setClave(BCrypt.withDefaults().hashToString(12, usuario.getClave().toCharArray()));
        return usuario;
    }


}
