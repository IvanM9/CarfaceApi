package com.app.tddt4iots.service.ChoferService;

import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.choferdto.PutChoferDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Usuario;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChoferService {
    Usuario saveChofer(CreateChoferDto chofer);
    Chofer editChofer(PutChoferDto chofer, Long id);
    Boolean deleteChofer(Long id);
    List<JSONObject> getChoferes();

    Boolean uploadPhoto(MultipartFile[] files, Long id);
}
