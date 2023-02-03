package com.app.tddt4iots.service.VehiculoService;

import com.app.tddt4iots.dtos.vehiculodto.CreateVehiculoDto;
import com.app.tddt4iots.entities.Vehiculo;

import java.util.Optional;

public interface VehiculoService {
    Optional<Vehiculo> addVehiculo(Long id_chofer, CreateVehiculoDto vehiculo);

}
