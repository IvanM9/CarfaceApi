package com.app.tddt4iots.dao;
 
 import com.app.tddt4iots.entities.Guardia;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Modifying;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.transaction.annotation.Transactional;

public interface GuardiaDao extends JpaRepository<Guardia, Long> {
    @Transactional
    @Modifying
    @Query("update Guardia g set g.estado = ?1 where g.id = ?2")
    int updateEstadoById(Boolean estado, Long id);
 }
