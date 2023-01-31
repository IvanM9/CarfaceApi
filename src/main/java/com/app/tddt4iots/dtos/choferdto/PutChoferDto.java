package com.app.tddt4iots.dtos.choferdto;

import com.app.tddt4iots.dtos.usuariodto.PutUserDto;
import com.app.tddt4iots.enums.TipoLicencia;
import lombok.Data;

@Data
public class PutChoferDto extends PutUserDto {
    public TipoLicencia tipolicencia;

}
