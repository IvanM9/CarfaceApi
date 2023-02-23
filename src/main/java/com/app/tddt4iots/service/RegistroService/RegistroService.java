package com.app.tddt4iots.service.RegistroService;

import com.app.tddt4iots.dtos.registrodto.CreateRegistroDto;
import com.app.tddt4iots.entities.Registro;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface RegistroService {

    @Transactional
    Registro insertRegistro(CreateRegistroDto datos);

    Registro getRegistroId(Long id);
    List<Registro> getRegistros();
}
