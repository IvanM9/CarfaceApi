package com.app.tddt4iots.dtos.vehiculodto;

import com.app.tddt4iots.enums.TipoVehiculo;
import lombok.Data;

@Data
public class CreateVehiculoDto {
    public String placa;
    public String marca;
    public String modelo;
    public String color;
    public String año;
    public TipoVehiculo tipoVehiculo;
}
