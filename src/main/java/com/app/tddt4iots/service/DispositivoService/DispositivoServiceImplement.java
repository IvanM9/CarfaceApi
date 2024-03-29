package com.app.tddt4iots.service.DispositivoService;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.app.tddt4iots.apis.GuardiaApi;
import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.UsuarioDao;
import com.app.tddt4iots.dtos.dispositivodto.CreateDispositivoDto;
import com.app.tddt4iots.dtos.usuariodto.CreateUserDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.entities.Vehiculo;
import com.app.tddt4iots.enums.Rol;
import com.app.tddt4iots.service.ChoferService.ChoferService;
import com.app.tddt4iots.service.ChoferService.ChoferServiceImplement;
import com.app.tddt4iots.utils.FilesUtil;
import com.app.tddt4iots.websocket.SocketClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;

import java.io.*;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DispositivoServiceImplement implements DispositivoService {
    @Autowired
    AmazonRekognition amazonRekognition;
    @Autowired
    FilesUtil filesUtil;
    @Autowired
    ChoferDao choferService;
    @Autowired
    SocketClient socketClient;

    @Autowired
    GuardiaApi guardiaApi;
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public Boolean compareFace(MultipartFile file) {
        try {
            PutObjectRequest foto = filesUtil.uploadFile(file, bucket, null);

            SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest().withImage(new Image()
                            .withS3Object(new S3Object().withName(foto.getKey()).withBucket(bucket))).withCollectionId("CarFaces")
                    .withMaxFaces(3);
            SearchFacesByImageResult result = amazonRekognition.searchFacesByImage(searchFacesByImageRequest);
            List<FaceMatch> lists = result.getFaceMatches();

            System.out.println("Detected labels for " + foto.getKey());

            Boolean match = false;
            if (!lists.isEmpty()) {
                for (FaceMatch label : lists) {
                    System.out.println(label.getFace() + ": Similarity is " + label.getSimilarity().toString());
                }
                String archivo = lists.get(0).getFace().getExternalImageId();
                match = true;
                Chofer chofer = choferService.findById(Long.valueOf(archivo.substring(0, archivo.indexOf("_")))).get();
                if (!sendChofer(chofer)) ;
                System.out.println("No tiene vehículos agregados");
                System.out.println("El chofer es: " + chofer.getUsuario().getNombres() + " " + chofer.getUsuario().getApellidos());
            } else {
                System.out.println("Faces Does not match");
                match = false;
            }
            filesUtil.deleteFile(bucket, foto.getKey());
            return match;
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario addDispositivo(CreateDispositivoDto dispositivo) {
        try {
            Usuario usuario = new Usuario();
            usuario.setRol(Rol.DISPOSITIVO);
            return saveDispositivo(dispositivo, usuario);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean deleteDispositivo(Long id) {
        try {
            Usuario dispositivo = usuarioDao.findById(id).orElseThrow();
            if (dispositivo.getRol() != Rol.DISPOSITIVO)
                return false;
            usuarioDao.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateDispositivo(CreateDispositivoDto dispositivoDto, Long id) {
        try {
            Usuario dispositivo = usuarioDao.findById(id).orElseThrow();
            if (dispositivo.getRol() != Rol.DISPOSITIVO)
                return false;
            saveDispositivo(dispositivoDto, dispositivo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<JSONObject> getAllDipositivos() {
        List<Usuario> lista = usuarioDao.findByRol(Rol.DISPOSITIVO);
        List<JSONObject> dispositivos = new ArrayList<>();
        for (Usuario dispositivo : lista) {
            JSONObject object = new JSONObject();
            object.put("id", dispositivo.getId());
            object.put("nombre", dispositivo.getNombres());
            object.put("correo", dispositivo.getCorreo());
            object.put("ubicacion", dispositivo.getDireccion());
            object.put("codigo", dispositivo.getCedula());
            object.put("fecha_creacion", dispositivo.getFechacreacion());
            object.put("fecha_modificacion", dispositivo.getFechamodificacion());
            dispositivos.add(object);
        }
        return dispositivos;
    }

    private Usuario saveDispositivo(CreateDispositivoDto dispositivo, Usuario usuario) {
        usuario.setCedula(dispositivo.getCodigo());
        usuario.setNombres(dispositivo.getNombre());
        usuario.setApellidos(dispositivo.getNombre());
        usuario.setDireccion(dispositivo.getUbicacion());
        if (dispositivo.getClave() != null || !dispositivo.getClave().isEmpty())
            usuario.setClave(BCrypt.withDefaults().hashToString(12, dispositivo.getClave().toCharArray()));
        usuario.setCorreo(dispositivo.getCodigo() + "@carface.com");
        Timestamp timestamp = new Timestamp(new Date().getTime());
        usuario.setFechacreacion(timestamp);
        usuario.setFechamodificacion(timestamp);
        return usuarioDao.save(usuario);
    }

    private Boolean sendChofer(Chofer chofer) {
        try {
            if (chofer.getVehiculo() == null || chofer.getVehiculo().isEmpty())
                return false;
            JSONObject datos = new JSONObject();
            datos.put("nombres", chofer.getUsuario().getNombres());
            datos.put("apellidos", chofer.getUsuario().getApellidos());
            datos.put("tipo_licencia", chofer.getLicencia());
            datos.put("ci", chofer.getUsuario().getCedula());
            datos.put("id_chofer", chofer.getUsuario().getId());
            List<JSONObject> vehiculos = new ArrayList<>();
            for (Vehiculo car : chofer.getVehiculo()) {
                JSONObject carro = new JSONObject();
                carro.put("id_vehiculo", car.getId());
                carro.put("modelo", car.getModelo());
                carro.put("marca", car.getMarca());
                carro.put("placa", car.getPlaca());
                carro.put("color", car.getColor());
                vehiculos.add(carro);
            }
            datos.put("vehiculos", vehiculos);
            socketClient.sendMessage(datos.toJSONString(), "a");
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
