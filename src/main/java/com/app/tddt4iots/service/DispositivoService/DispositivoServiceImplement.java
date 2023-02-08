package com.app.tddt4iots.service.DispositivoService;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.app.tddt4iots.utils.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

@Service
public class DispositivoServiceImplement implements DispositivoService {
    @Autowired
    AmazonRekognition amazonRekognition;
    @Autowired
    FilesUtil filesUtil;
    @Value("${aws.s3.bucket}")
    private String bucket;

    @Override
    public Boolean compareFace(MultipartFile file) {
        try {
            PutObjectRequest foto = filesUtil.uploadFile(file, bucket);
            //TODO: Crear una colección para reconocer los rostros

            SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest().withImage(new Image()
                    .withS3Object(new S3Object().withName(foto.getKey()).withBucket(bucket))).withCollectionId(null)
                    .withMaxFaces(5);
            SearchFacesByImageResult result = amazonRekognition.searchFacesByImage(searchFacesByImageRequest);
            List<FaceMatch> lists = result.getFaceMatches();

            System.out.println("Detected labels for " + foto.getKey());

            if (!lists.isEmpty()) {
                for (FaceMatch label : lists) {
                    System.out.println(label.getFace() + ": Similarity is " + label.getSimilarity().toString());
                }
                return true;
            } else {
                System.out.println("Faces Does not match");
                return false;
            }
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
            return false;
        }
    }
}
