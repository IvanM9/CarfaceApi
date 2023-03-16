package com.app.tddt4iots.service.ChoferService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.FotochoferDao;
import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.choferdto.CreateChoferDto;
import com.app.tddt4iots.dtos.choferdto.PutChoferDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Fotochofer;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.UsuarioService.UsuarioServiceImplement;
import com.app.tddt4iots.utils.FilesUtil;

import jakarta.transaction.Transactional;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ChoferServiceImplement implements ChoferService {

    @Autowired
    ChoferDao choferDao;
    @Autowired
    FotochoferDao fotochoferDao;

    @Autowired
    UsuarioDao usuario;

    @Autowired
    UsuarioServiceImplement usuarioServiceImplement;

    @Autowired
    AmazonS3 amazonS3;
    @Autowired
    FilesUtil filesUtil;

    @Value("${aws.s3.bucket_rekognition}")
    private String bucketName;

    @Override
    @Transactional
    public Usuario saveChofer(CreateChoferDto chofer) {
        try {
            CreateUserDto user = chofer;
            Chofer chofer1 = new Chofer();
            chofer1.setLicencia(chofer.getTipolicencia());
            Usuario usuario1 = usuarioServiceImplement.createUsuario(user, Rol.CHOFER);
            chofer1.setUsuario(usuario1);
            usuario1.setChofer(chofer1);
            System.out.println(usuario1);
            return usuario.save(usuario1);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Chofer editChofer(PutChoferDto chofer, Long id) {
        Chofer chofer1 = this.choferDao.findById(id).orElseThrow();
        chofer1.setLicencia(chofer.getTipolicencia());
        chofer1.getUsuario().setNombres(chofer.getNombre());
        chofer1.getUsuario().setApellidos(chofer.getApellido());
        chofer1.getUsuario().setDireccion(chofer.getDireccion());
        chofer1.getUsuario().setTelefono(chofer.getTelefono());
        chofer1.getUsuario().setFechamodificacion(new Timestamp(new Date().getTime()));

        return this.choferDao.save(chofer1);
    }

    @Override
    public Boolean deleteChofer(Long id) {
        try {
            if (usuario.findById(id).orElseThrow().getRol() != Rol.CHOFER)
                return false;
            usuario.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<JSONObject> getChoferes() {
        try {
            List<Usuario> choferes = usuario.findByRol(Rol.CHOFER);
            List<JSONObject> datos = new ArrayList<>();
            for (Usuario chofer : choferes) {
                JSONObject usuario = new JSONObject();
                usuario.put("id", chofer.getId());
                usuario.put("telefono", chofer.getTelefono());
                usuario.put("ci", chofer.getCedula());
                usuario.put("tipolicencia", chofer.getChofer().getLicencia());
                usuario.put("correo", chofer.getCorreo());
                usuario.put("nombres", chofer.getNombres());
                usuario.put("apellidos", chofer.getApellidos());
                datos.add(usuario);
            }
            return datos;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean uploadPhoto(MultipartFile[] files, Long id) {
        try {
            Usuario usuario1 = usuario.findById(id).orElseThrow();
            List<Fotochofer> fotos = new ArrayList<>();
            AtomicReference<Boolean> success = new AtomicReference<>(false);
            Arrays.asList(files).stream().forEach(file -> {
                try {
                    PutObjectRequest request = filesUtil.uploadFile(file, bucketName, usuario1.getId());
                    filesUtil.indexFace(bucketName, request.getKey());
                    fotos.add(newFotoChofer(file.getOriginalFilename(), request.getKey(), usuario1.getChofer()));
                    success.set(true);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    success.set(false);
                }
            });
            if (!fotos.isEmpty()) {
                addFotosChofer(fotos, usuario1.getChofer());
            }
            return success.get();
        } catch (Exception e) {
            return null;
        }
    }


    @Transactional
    private Fotochofer newFotoChofer(String nombre, String url, Chofer chofer) {
        Fotochofer fotochofer = new Fotochofer();
        fotochofer.setNombre(nombre);
        fotochofer.setUrl(url);
        fotochofer.setChofer(chofer);

        return fotochoferDao.save(fotochofer);
    }

    @Transactional
    private Boolean addFotosChofer(List<Fotochofer> fotos, Chofer chofer) {
        try {
            chofer.getFotochofer().addAll(fotos);
            choferDao.save(chofer);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
