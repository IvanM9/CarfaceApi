package com.app.tddt4iots.service.ChoferService;

import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoferServiceImplement implements ChoferService {

    @Autowired
    ChoferDao choferDao;

    @Autowired
    UsuarioDao usuario;

    @Autowired
    UsuarioServiceImplement usuarioServiceImplement;

    @Override
    @Transactional
    public Usuario saveChofer(CreateChoferDto chofer) {
        CreateUserDto user = chofer;
        Chofer chofer1 = new Chofer();
        chofer1.setLicencia(chofer.getTipolicencia());
        Usuario usuario1 = usuarioServiceImplement.createUsuario(user, Rol.CHOFER);
        chofer1.setUsuario(usuario1);
        usuario1.setChofer(chofer1);
        System.out.println(usuario1);
        return usuario.save(usuario1);
    }
}
