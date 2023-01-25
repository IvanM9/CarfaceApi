package com.app.tddt4iots.service.UsuarioService;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.septimo.carface.carface.dtos.usuariodto.PutUserDto;

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
            Usuario chofer = repository.findOneByCorreo(correo).orElseThrow();
            object.put("nombre", chofer.getNombres());
            object.put("apellido", chofer.getApellidos());
            object.put("ci", chofer.getCedula());
            object.put("fotos", chofer.getChofer().getFotochofer().toArray());
            return object;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Usuario saveUsuario(CreateUserDto usuario) {
        return repository.save(createUsuario(usuario, Rol.CHOFER));
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

    @Override
    public Boolean updloadPhoto(MultipartFile[] files) {
        // TODO: Método para subir fotos a la nube
        try {
            AtomicReference<Boolean> success = new AtomicReference<>(false);
            String localPath = Paths.get("").toAbsolutePath().toString() +
                    "/src/main/resources/static/images";
            ArrayList<String> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                //success.set(FileUtils.upload(file, localPath, file.getOriginalFilename()));
                success.set(true);
            });
            return success.get();
        } catch (Exception e) {
            return null;
        }
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