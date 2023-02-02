package com.app.tddt4iots;

import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CarfaceApplication implements CommandLineRunner {
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    UsuarioServiceImplement usuarioServiceImplement;

    @Value("${admin.correo}")
    String correo;
    @Value("${admin.clave}")
    String clave;

    public static void main(String[] args) {
        SpringApplication.run(CarfaceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            if (usuarioDao.findOneByCorreo(correo).isEmpty()) {
                CreateUserDto usuario = new CreateUserDto();
                usuario.setCorreo(correo);
                usuario.setClave(clave);
                usuario.setNombre("admin");
                usuario.setApellido("admin");
                usuario.setCi("admin");
                Usuario user = usuarioDao.save(usuarioServiceImplement.createUsuario(usuario, Rol.ADMINISTRADOR));
                System.out.println("Administrador creado");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
