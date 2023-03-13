package com.app.tddt4iots.service.DispositivoService;

import com.app.tddt4iots.dtos.dispositivodto.CreateDispositivoDto;
import com.app.tddt4iots.entities.Usuario;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DispositivoService {
    Boolean compareFace(MultipartFile file);
    Usuario addDispositivo(CreateDispositivoDto dispositivo);
    Boolean deleteDispositivo(Long id);
    Boolean updateDispositivo(CreateDispositivoDto dispositivoDto, Long id);

    List<JSONObject> getAllDipositivos();
}
