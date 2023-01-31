package com.app.tddt4iots.service.ChoferService;

import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.choferdto.GetChoferDto;
import com.app.tddt4iots.dtos.choferdto.PutChoferDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface ChoferService {
    Usuario saveChofer(CreateChoferDto chofer);
    Chofer editChofer(PutChoferDto chofer, Long id);
    Boolean deleteChofer(Long id);
    List<GetChoferDto> getChoferes();
}
