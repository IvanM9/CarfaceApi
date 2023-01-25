package com.app.tddt4iots.dao;
 
 import com.app.tddt4iots.entities.Registro;
 import org.springframework.data.jpa.repository.JpaRepository;
 
 public interface RegistroDao extends JpaRepository<Registro, Long> {
 }
