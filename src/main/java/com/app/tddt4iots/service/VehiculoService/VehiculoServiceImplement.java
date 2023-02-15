package com.app.tddt4iots.service.VehiculoService;

import com.app.tddt4iots.dao.ChoferDao;
import com.app.tddt4iots.dao.FotovehiculoDao;
import com.app.tddt4iots.dao.VehiculoDao;
import com.app.tddt4iots.dtos.vehiculodto.CreateVehiculoDto;
import com.app.tddt4iots.entities.Chofer;
import com.app.tddt4iots.entities.Fotovehiculo;
import com.app.tddt4iots.entities.Usuario;
import com.app.tddt4iots.entities.Vehiculo;
import com.app.tddt4iots.utils.FilesUtil;
import com.amazonaws.services.s3.model.PutObjectRequest;


import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VehiculoServiceImplement implements VehiculoService{

    @Autowired
    VehiculoDao vehiculoDao;
    @Autowired
    FotovehiculoDao fotovehiculoDao;
    @Autowired
    ChoferDao choferDao;
    @Autowired
    FilesUtil filesUtil;

    @Value("${aws.s3.bucket_2}")
    private String bucketName;

    @Override
    @Transactional
    public Optional<Vehiculo> addVehiculo(Long id_chofer, CreateVehiculoDto vehiculo) {
        try {
            Chofer chofer = choferDao.findById(id_chofer).orElseThrow();
            Vehiculo vehiculo1 = new Vehiculo();
            vehiculo1.setPlaca(vehiculo.getPlaca());
            vehiculo1.setMarca(vehiculo.getMarca());
            vehiculo1.setModelo(vehiculo.getModelo());
            vehiculo1.setColor(vehiculo.getColor());
            vehiculo1.setAnio(vehiculo.getAño());
            vehiculo1.setTipoVehiculo(vehiculo.getTipoVehiculo());
            Timestamp timestamp = new Timestamp(new Date().getTime());
            vehiculo1.setFechacreacion(timestamp);
            vehiculo1.setFechamodificacion(timestamp);
            vehiculo1.setChofer(chofer);
            vehiculoDao.save(vehiculo1);
            chofer.getVehiculo().add(vehiculo1);
            choferDao.save(chofer);
            return Optional.of(vehiculo1);
        }
        catch (Exception e){
            return Optional.empty();
        }
    }
	@Override
	public Optional<Vehiculo> updateVehiculo(Long id, CreateVehiculoDto vehiculo) {
		try{
            Vehiculo vehiculo1 = vehiculoDao.findById(id).orElseThrow();
                vehiculo1.setPlaca(vehiculo.getPlaca());
                vehiculo1.setMarca(vehiculo.getMarca());
                vehiculo1.setModelo(vehiculo.getModelo());
                vehiculo1.setColor(vehiculo.getColor());
                vehiculo1.setAnio(vehiculo.getAño());
                return Optional.of(vehiculo1);
        }
        catch(Exception e){
		return Optional.empty();
        }
	}
	@Override
	public Boolean uploadPhoto(MultipartFile[] files, Long id_chofer, Long id_vehiculo) {
        Vehiculo vehiculo = vehiculoDao.findById(id_vehiculo).orElseThrow();
        if(!vehiculo.getChofer().getUsuario().getId().equals(id_chofer))
            return false;
        List<Fotovehiculo> fotos = new ArrayList<>();
        AtomicReference<Boolean> success = new AtomicReference<>(false);
        Arrays.asList(files).stream().forEach(file->{
            try {
                PutObjectRequest request =  filesUtil.uploadFile(file, bucketName, null);
                fotos.add(newFotoVehiculo(file.getOriginalFilename(), request.getKey(), vehiculo));
                success.set(true);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                success.set(false);
            }
        });
        if(!fotos.isEmpty())
            addFotosChofer(fotos, vehiculo);
		return success.get();
	}

    @Transactional
    private Fotovehiculo newFotoVehiculo(String nombre, String url, Vehiculo vehiculo) {
        Fotovehiculo fotovehiculo = new Fotovehiculo();
        fotovehiculo.setNombre(nombre);
        fotovehiculo.setUrl(url);
        fotovehiculo.setVehiculo(vehiculo);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        fotovehiculo.setFechacreacion(timestamp);
        fotovehiculo.setFechamodificacion(timestamp);

        return fotovehiculoDao.save(fotovehiculo);
    }

    @Transactional
    private Boolean addFotosChofer(List<Fotovehiculo> fotos, Vehiculo vehiculo) {
        try {
            vehiculo.getFotovehiculo().addAll(fotos);
            vehiculoDao.save(vehiculo);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
