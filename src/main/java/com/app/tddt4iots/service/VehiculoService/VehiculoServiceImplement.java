package com.app.tddt4iots.service.VehiculoService;

import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.VehiculoDao;
import com.app.tddt4iots.dtos.vehiculodto.CreateVehiculoDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Vehiculo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoServiceImplement implements VehiculoService{

    @Autowired
    VehiculoDao vehiculoDao;
    @Autowired
    ChoferDao choferDao;
    @Override
    @Transactional
    public Optional<Vehiculo> addVehiculo(Long id_chofer, CreateVehiculoDto vehiculo) {
        try {
            Chofer chofer = choferDao.findById(id_chofer).orElseThrow();
            Vehiculo vehiculo1 = new Vehiculo();
            vehiculo1.setPlaca(vehiculo.getPlaca());
            vehiculo1.setMarca(vehiculo.getMarca());
            vehiculo1.setModelo(vehiculo.getModelo());
            vehiculo1.setColor(vehiculo.getColor());
            vehiculo1.setAnio(vehiculo.getAño());
            vehiculo1.setTipoVehiculo(vehiculo.getTipoVehiculo());
            vehiculo1.setChofer(chofer);
            vehiculoDao.save(vehiculo1);
            chofer.getVehiculo().add(vehiculo1);
            choferDao.save(chofer);
            return Optional.of(vehiculo1);
        }
        catch (Exception e){
            return Optional.empty();
        }
    }
}
