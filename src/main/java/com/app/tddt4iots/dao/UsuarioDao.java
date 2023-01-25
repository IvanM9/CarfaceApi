package com.app.tddt4iots.dao;
 
 import com.app.tddt4iots.entities.Usuario;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Modifying;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.CrudRepository;
 import org.springframework.transaction.annotation.Transactional;

 import java.sql.Timestamp;
 import java.util.Optional;

public interface UsuarioDao extends JpaRepository<Usuario, Long>, CrudRepository<Usuario, Long> {
    @Transactional
    @Modifying
    @Query("update Usuario u set u.nombres = ?1, u.apellidos = ?2 where u.id = ?3")
    int updateNombresAndApellidosById(String nombres, String apellidos, Long id);
  Optional<Usuario> findOneUsuarioByCorreo(String correo);

}
