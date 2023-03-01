package com.app.tddt4iots.dao;
 
 import com.app.tddt4iots.entities.Vehiculo;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;

 import java.util.Optional;

public interface VehiculoDao extends JpaRepository<Vehiculo, Long> {
    @Query("select v from Vehiculo v where v.placa = ?1")
    Optional<Vehiculo> findByPlaca(String placa);
 }
