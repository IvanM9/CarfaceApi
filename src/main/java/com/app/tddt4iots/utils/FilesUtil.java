package com.app.tddt4iots.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class FilesUtil {
    @Autowired
    AmazonS3 amazonS3;

    @Autowired
    AmazonRekognition amazonRekognition;
    

    public PutObjectRequest uploadFile(MultipartFile file, String bucket){
        File mainFile = new File(file.getOriginalFilename());

        try (FileOutputStream stream = new FileOutputStream(mainFile)){
            stream.write(file.getBytes());
            String newFileName = System.currentTimeMillis() + "_" + mainFile.getName();
            PutObjectRequest request = new PutObjectRequest(bucket, newFileName, mainFile);
            amazonS3.putObject(request);
            System.out.println("Eliminado "+mainFile.getName()+":"+ mainFile.delete());
            return request;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CreateColletionResult createColletionRekognition(){
        return null;
    }
}
