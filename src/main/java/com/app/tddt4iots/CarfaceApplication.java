package com.app.tddt4iots;

import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import com.app.tddt4iots.utils.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class CarfaceApplication implements CommandLineRunner {

    @Autowired
    UsuarioServiceImplement usuarioServiceImplement;
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    FilesUtil filesUtil;

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
            if (usuarioServiceImplement.getUsuarioByEmail(correo) == null) {
                filesUtil.deleteColletion("CarFaces");
                CreateUserDto usuario = new CreateUserDto();
                usuario.setCorreo(correo);
                usuario.setClave(clave);
                usuario.setNombre("admin");
                usuario.setApellido("admin");
                usuario.setCi("admin");
                usuarioDao.save(usuarioServiceImplement.createUsuario(usuario, Rol.ADMINISTRADOR));
                System.out.println("Administrador creado");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
            }
        };
    }
}
