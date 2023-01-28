package com.app.tddt4iots.apis;

import com.app.tddt4iots.security.SessionService;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("sesion")
public class SesionApi {
    @Autowired
    SessionService sessionService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader String correo, @RequestHeader String clave) {
        JSONObject object = new JSONObject();
        object.put("token", sessionService.login(correo, clave));
        return new ResponseEntity<>(
                object,
                object.get("token") == null ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);

    }
}
