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
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<JSONObject> getAllGuardias() {
        try {
            List<Usuario> usuarios = usuarioRepository.findByRol(Rol.GUARDIA);
            List<JSONObject> guardias = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                JSONObject guardia = new JSONObject();
                guardia.put("id", usuario.getId());
                guardia.put("nombre", usuario.getNombres() + " " + usuario.getApellidos());
                guardia.put("telefono", usuario.getTelefono());
                guardia.put("ci", usuario.getCedula());
                guardia.put("correo", usuario.getCorreo());
                guardia.put("empresa", usuario.getGuardia().getEmpresa());
                guardia.put("estado", usuario.getGuardia().getEstado());
                guardias.add(guardia);
            }
            return guardias;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean updateEstado(Long id, Boolean estado) {
        return guardiaRepository.updateEstadoById(estado, id) == 1;
    }

    @Override
    @Transactional
    public Boolean updateGuardia(CreateGuardiaDto guardiaDto, Long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id).orElseThrow();
            if (usuario.getRol() != Rol.GUARDIA) return null;
            usuario.setNombres(guardiaDto.getNombre());
            usuario.setApellidos(guardiaDto.getApellido());
            usuario.setCedula(guardiaDto.getCi());
            usuario.getGuardia().setEmpresa(guardiaDto.getCompania());
            usuario.getGuardia().setFecha_inicio(guardiaDto.getFecha_inicio());
            usuario.getGuardia().setFecha_fin(guardiaDto.getFecha_fin());
            guardiaRepository.save(usuario.getGuardia());
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
