package com.app.tddt4iots.dao;

import com.app.tddt4iots.dtos.choferdto.GetChoferDto;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UsuarioDao extends JpaRepository<Usuario, Long>, CrudRepository<Usuario, Long> {
    List<Usuario> findByRol(Rol rol);
    @Query("select new com.app.tddt4iots.dtos.choferdto.GetChoferDto(u.id,u.nombres,u.apellidos, u.cedula, u.correo,u.telefono,u.direccion,u.fechacreacion,u.fechamodificacion, c.licencia)" +
            " from Usuario u inner join Chofer c where u.rol = ?1")
    List<GetChoferDto> queryByRol(Rol rol);


    Optional<Usuario> findOneByCorreo(String correo);

    @Transactional
    @Modifying
    @Query("update Usuario u set u.nombres = ?1, u.apellidos = ?2 where u.id = ?3")
    int updateNombresAndApellidosById(String nombres, String apellidos, Long id);


}
