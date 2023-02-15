package com.app.tddt4iots.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.backupstorage.model.DeleteObjectResult;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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


    public PutObjectRequest uploadFile(MultipartFile file, String bucket, Long id) {
        File mainFile = new File(file.getOriginalFilename());

        try (FileOutputStream stream = new FileOutputStream(mainFile)) {
            stream.write(file.getBytes());
            String nombre = id == null ? "" : id + "_";
            String newFileName = nombre + System.currentTimeMillis() + "_" + mainFile.getName().substring(mainFile.getName().lastIndexOf("."));
            PutObjectRequest request = new PutObjectRequest(bucket, newFileName, mainFile);
            amazonS3.putObject(request);
            System.out.println("Eliminado " + mainFile.getName() + ":" + mainFile.delete());
            return request;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CreateCollectionResult createColletionRekognition() {
        String collectionId = "CarFaces";
        System.out.println("Creating collection: " +
                collectionId);
        try {
            CreateCollectionRequest request = new CreateCollectionRequest()
                    .withCollectionId(collectionId);
            CreateCollectionResult createCollectionResult = amazonRekognition.createCollection(request);
            return createCollectionResult;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private ListCollectionsResult collections() {
        try {
            System.out.println("Listing collections");
            int limit = 10;
            ListCollectionsResult listCollectionsResult = null;
            String paginationToken = null;
            do {
                if (listCollectionsResult != null) {
                    paginationToken = listCollectionsResult.getNextToken();
                }
                ListCollectionsRequest listCollectionsRequest = new ListCollectionsRequest()
                        .withMaxResults(limit)
                        .withNextToken(paginationToken);
                listCollectionsResult = amazonRekognition.listCollections(listCollectionsRequest);
            } while (listCollectionsResult != null && listCollectionsResult.getNextToken() !=
                    null);

            return listCollectionsResult;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }


    public IndexFacesResult indexFace(String bucket, String photo) {
        if (!collections().getCollectionIds().contains("CarFaces")) {
            createColletionRekognition();
        }
        try {
            Image image = new Image()
                    .withS3Object(new S3Object()
                            .withBucket(bucket)
                            .withName(photo));

            IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                    .withImage(image)
                    .withQualityFilter(QualityFilter.AUTO)
                    .withMaxFaces(1)
                    .withCollectionId("CarFaces")
                    .withExternalImageId(photo)
                    .withDetectionAttributes("DEFAULT");

            IndexFacesResult indexFacesResult = amazonRekognition.indexFaces(indexFacesRequest);

            List<UnindexedFace> unindexedFaces = indexFacesResult.getUnindexedFaces();
            System.out.println("Faces not indexed:");
            for (UnindexedFace unindexedFace : unindexedFaces) {
                deleteFile(bucket, photo);
                System.out.println("  Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
                System.out.println("  Reasons:");
                for (String reason : unindexedFace.getReasons()) {
                    System.out.println("   " + reason);
                }
            }
            return indexFacesResult;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public Boolean deleteFile(String bucket, String image_key) {
        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, image_key);
            amazonS3.deleteObject(deleteObjectRequest);
            return true;
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            return false;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return false;
        }
    }
}
