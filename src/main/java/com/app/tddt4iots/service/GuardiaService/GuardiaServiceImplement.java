package com.app.tddt4iots.service.GuardiaService;


import com.app.tddt4iots.dao.GuardiaDao;
import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.guardiadto.CreateGuardiaDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Guardia;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.ScreenSleepEvent;

@Service
public class GuardiaServiceImplement implements GuardiaService {
    @Autowired
    GuardiaDao guardiaRepository;
    @Autowired
    UsuarioDao usuarioRepository;

    @Autowired
    UsuarioServiceImplement usuarioServiceImplement;



    @Override
    @Transactional
    public Usuario saveGuardia(CreateGuardiaDto datos) {
        CreateUserDto usuario = new CreateUserDto();
        Guardia guardia = new Guardia();

        usuario.setNombre(datos.getNombre());
        usuario.setApellido(datos.getApellido());
        usuario.setCi(datos.getCi());
        usuario.setCorreo(datos.getCorreo());
        usuario.setClave(datos.getClave());

        guardia.setEmpresa(datos.getCompania());
        guardia.setEmpresa(datos.getFecha_inicio());
        guardia.setEmpresa(datos.getFecha_fin());

        Usuario user = usuarioServiceImplement.createUsuario(usuario, Rol.Guardia);
        guardia.setUsuario(user);
        //guardia.set(true); TODO:  asignar un estado para saber si sigue en el trabajo o no
        user.setGuardia(guardia);
        System.out.println(guardia);
        return usuarioRepository.save(user);
    }
}
