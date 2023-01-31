package com.app.tddt4iots.service.ChoferService;

import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.choferdto.GetChoferDto;
import com.app.tddt4iots.dtos.choferdto.PutChoferDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Chofer editChofer(PutChoferDto chofer, Long id) {
        Chofer chofer1 = this.choferDao.findById(id).orElseThrow();
        chofer1.setLicencia(chofer.getTipolicencia());
        chofer1.getUsuario().setNombres(chofer.getNombre());
        chofer1.getUsuario().setApellidos(chofer.getApellido());
        chofer1.getUsuario().setDireccion(chofer.getDireccion());
        chofer1.getUsuario().setTelefono(chofer.getTelefono());
        chofer1.getUsuario().setFechamodificacion(new Timestamp(new Date().getTime()));

        return this.choferDao.save(chofer1);
    }

    @Override
    public Boolean deleteChofer(Long id) {
        return null;
    }

    @Override
    public List<GetChoferDto> getChoferes() {
        return usuario.queryByRol(Rol.CHOFER);

    }
}
