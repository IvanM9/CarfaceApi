package com.app.tddt4iots.dao;
 
 import com.app.tddt4iots.entities.Chofer;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;

 import java.util.Optional;

public interface ChoferDao extends JpaRepository<Chofer, Long> {
    @Query("select c from Chofer c inner join c.vehiculo vehiculo where vehiculo.placa = ?1")
    Optional<Chofer> findByVehiculo_Placa(String placa);
 }
