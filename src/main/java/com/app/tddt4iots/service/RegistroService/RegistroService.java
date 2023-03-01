package com.app.tddt4iots.service.RegistroService;

import com.app.tddt4iots.dtos.registrodto.CreateRegistroDto;
import com.app.tddt4iots.entities.Registro;
import jakarta.transaction.Transactional;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Optional;

public interface RegistroService {

    @Transactional
    Registro insertRegistro(CreateRegistroDto datos, Long id_guardia);

    Registro getRegistroId(Long id);
    List<JSONObject> getRegistros();
}
