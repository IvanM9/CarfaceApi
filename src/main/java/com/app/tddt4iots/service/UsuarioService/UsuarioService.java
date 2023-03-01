package com.app.tddt4iots.service.UsuarioService;

import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.dtos.usuariodto.PutUserDto;
import com.app.tddt4iots.entities.Usuario;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    ArrayList<Usuario> getUsuarios();
    JSONObject getUsuarioById(long id);
    JSONObject getUsuarioByEmail(String correo);


    Boolean deleteUsuario(long id);

    Usuario editUsuario(Long id, PutUserDto usuario);


}
