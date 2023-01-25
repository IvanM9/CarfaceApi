package com.app.tddt4iots.dao;
 
 import com.app.tddt4iots.entities.Vehiculo;
 import org.springframework.data.jpa.repository.JpaRepository;
 
 public interface VehiculoDao extends JpaRepository<Vehiculo, Long> {
 }
