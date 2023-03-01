package com.app.tddt4iots.service.GuardiaService;


import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.GuardiaDao;
import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dao.VehiculoDao;
import com.app.tddt4iots.dtos.guardiadto.CreateGuardiaDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Guardia;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.entities.Vehiculo;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.json.simple.JSONObject;
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
    ChoferDao choferDao;

    @Autowired
    VehiculoDao vehiculoDao;

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
        guardia.setFecha_inicio(datos.getFecha_inicio());
        guardia.setFecha_fin(datos.getFecha_fin());

        Usuario user = usuarioServiceImplement.createUsuario(usuario, Rol.GUARDIA);
        guardia.setUsuario(user);
        guardia.setEstado(true);
        user.setGuardia(guardia);
        System.out.println(guardia);
        return usuarioRepository.save(user);
    }

    @Override
    public JSONObject escanearPlaca(String placa) {
        try {
            Vehiculo vehiculo = vehiculoDao.findByPlaca(placa).orElseThrow();
            JSONObject json = new JSONObject();
            json.put("chofer", vehiculo.getChofer().getUsuario().getNombres() + " " + vehiculo.getChofer().getUsuario().getApellidos());
            json.put("tipo_licencia", vehiculo.getChofer().getLicencia());
            json.put("ci", vehiculo.getChofer().getUsuario().getCedula());
            json.put("id_chofer", vehiculo.getChofer().getUsuario().getId());
            json.put("id_vehiculo", vehiculo.getId());
            json.put("marca_vehiculo", vehiculo.getMarca());
            json.put("modelo_vehiculo", vehiculo.getModelo());
            json.put("color_vehiculo", vehiculo.getColor());
            return json;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
