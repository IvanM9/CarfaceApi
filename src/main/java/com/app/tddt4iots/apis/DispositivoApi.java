package com.app.tddt4iots.apis;

import com.app.tddt4iots.service.DispositivoService.DispositivoServiceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dispositivo")
public class DispositivoApi {
    @Autowired
    DispositivoServiceImplement dispositivoService;

    @PostMapping("/comparar")
    public ResponseEntity<?> compareFace(@RequestPart(value = "files") MultipartFile file){
        Boolean compare =  dispositivoService.compareFace(file);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
