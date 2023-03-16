package com.app.tddt4iots.service.VehiculoService;

import com.app.tddt4iots.dtos.vehiculodto.CreateVehiculoDto;
import com.app.tddt4iots.entities.Vehiculo;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;

public interface VehiculoService {
    Optional<Vehiculo> addVehiculo(Long id_chofer, CreateVehiculoDto vehiculo);

    Optional<Vehiculo> updateVehiculo(Long id, CreateVehiculoDto vehiculo, Long id_chofer);
    Boolean uploadPhoto(MultipartFile[] files, Long id_chofer, Long id_vehiculo);
    Boolean deleteVehiculo(Long id_vehiculo, Long id_chofer);

}
