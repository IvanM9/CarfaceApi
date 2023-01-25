package com.app.tddt4iots.dtos.choferdto;

import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.enums.TipoLicencia;
import lombok.Data;

@Data
public class CreateChoferDto extends CreateUserDto {
    private TipoLicencia tipolicencia;

}
