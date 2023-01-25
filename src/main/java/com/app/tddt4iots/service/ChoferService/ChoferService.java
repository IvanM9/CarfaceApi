package com.app.tddt4iots.service.ChoferService;

import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Usuario;

public interface ChoferService {
    Usuario saveChofer(CreateChoferDto chofer);
}
