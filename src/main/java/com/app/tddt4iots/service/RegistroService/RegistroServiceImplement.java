package com.app.tddt4iots.service.RegistroService;

import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.GuardiaDao;
import com.app.tddt4iots.dao.RegistroDao;
import com.app.tddt4iots.dao.VehiculoDao;
import com.app.tddt4iots.dtos.registrodto.CreateRegistroDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Guardia;
import com.app.tddt4iots.entities.Registro;
import com.app.tddt4iots.entities.Vehiculo;
import jakarta.transaction.Transactional;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RegistroServiceImplement implements RegistroService {

    @Autowired
    RegistroDao registroDao;

    @Autowired
    ChoferDao choferDao;
    @Autowired
    GuardiaDao guardiaDao;
    @Autowired
    VehiculoDao vehiculoDao;

    @Transactional
    @Override
    public Registro insertRegistro(CreateRegistroDto datos, Long id_guardia) {
        try {
            Registro registro = new Registro();
            registro.setFecha(new Timestamp(new Date().getTime()));
            registro.setTipo(datos.getTipo());
            registro.setObservaciones(datos.getObservaciones());
            Chofer chofer = choferDao.findById(datos.getId_chofer()).orElseThrow();
            registro.setChofer(chofer);
            Vehiculo vehiculo = vehiculoDao.findById(datos.getId_vehiculo()).orElseThrow();
            registro.setVehiculo(vehiculo);
            Guardia guardia = guardiaDao.findById(id_guardia).orElseThrow();
            registro.setGuardia(guardia);
            registro = registroDao.save(registro);

            chofer.getRegistro().add(registro);
            choferDao.save(chofer);
            vehiculo.getRegistros().add(registro);
            vehiculoDao.save(vehiculo);
            guardia.getRegistros().add(registro);
            guardiaDao.save(guardia);
            return registro;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Registro getRegistroId(Long id) {
        try {
            return registroDao.findById(id).orElseThrow();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<JSONObject> getRegistros() {
        try {
            ArrayList<JSONObject> registros = new ArrayList<>();
            for (Registro registro : registroDao.findAll()) {
                JSONObject object = new JSONObject();
                object.put("chofer", registro.getChofer().getUsuario().getNombres() + " " + registro.getChofer().getUsuario().getApellidos());
                object.put("marca_vehiculo", registro.getVehiculo().getMarca());
                object.put("modelo_vehiculo", registro.getVehiculo().getModelo());
                object.put("color_vehiculo", registro.getVehiculo().getColor());
                object.put("placa", registro.getVehiculo().getPlaca());
                object.put("guardia", registro.getGuardia().getUsuario().getNombres() + " " + registro.getGuardia().getUsuario().getApellidos());
                object.put("fecha", registro.getFecha());
                object.put("id_vehiculo", registro.getVehiculo().getId());
                object.put("id_guardia", registro.getGuardia().getUsuario().getId());
                object.put("id_chofer", registro.getGuardia().getUsuario().getId());
                registros.add(object);
            }

            return registros;
        } catch (Exception e) {
            return null;
        }
    }

}
