package com.app.tddt4iots.service.RegistroService;

import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.RegistroDao;
import com.app.tddt4iots.dao.VehiculoDao;
import com.app.tddt4iots.dtos.registrodto.CreateRegistroDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Registro;
import com.app.tddt4iots.entities.Vehiculo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class RegistroServiceImplement implements RegistroService {

    @Autowired
    RegistroDao registroDao;

    @Autowired
    ChoferDao choferDao;
    @Autowired
    VehiculoDao vehiculoDao;

    @Transactional
    @Override
    public Registro insertRegistro(CreateRegistroDto datos) {
        try{
            Registro registro = new Registro();
            registro.setFecha(new Timestamp(new Date().getTime()));
            registro.setTipo(datos.getTipo());
            registro.setObservaciones(datos.getObservaciones());
            Chofer chofer = choferDao.findById(datos.getId_chofer()).orElseThrow();
            registro.setChofer(chofer);
            Vehiculo vehiculo = vehiculoDao.findById(datos.getId_vehiculo()).orElseThrow();
            registro.setVehiculo(vehiculo);
            registro = registroDao.save(registro);

            chofer.getRegistro().add(registro);
            choferDao.save(chofer);
            vehiculo.getRegistro().add(registro);
            vehiculoDao.save(vehiculo);
            return registro;
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Registro getRegistroId(Long id) {
        try{
            return registroDao.findById(id).orElseThrow();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Registro> getRegistros() {
        try{
            return registroDao.findAll();
        }
        catch (Exception e) {
            return null;
        }
    }

}
