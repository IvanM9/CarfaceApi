package com.app.tddt4iots.service.GuardiaService;


import com.app.tddt4iots.dtos.guardiadto.CreateGuardiaDto;
import com.app.tddt4iots.entities.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface GuardiaService {
    Usuario saveGuardia(CreateGuardiaDto datos);
}
