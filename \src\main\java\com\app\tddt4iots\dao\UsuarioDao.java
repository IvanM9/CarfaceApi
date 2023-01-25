package com.app.tddt4iots.dao;
 
 import com.app.tddt4iots.entities.Usuario;
 import org.springframework.data.jpa.repository.JpaRepository;
 
 public interface UsuarioDao extends JpaRepository<Usuario, Long> {
 }
