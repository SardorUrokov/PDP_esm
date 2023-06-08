package com.example.pdp_esm.service.Implements;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioService {
    private static final String ACCESS_KEY = "j72FXldP0cTq6NxHAgiv";
    private static final String SECRET_KEY = "sFOArkuN9ZSxnFZsPlBKhfBPK5muzHWsxwxwyfTJ";
    private static final String ENDPOINT = "http://192.168.1.100:9000";
    private static final String CERTIFICATES_PATH = "C:\\Users\\user\\Desktop\\PDP_Certificates";
    private static final int CONNECTION_TIMEOUT_SECONDS = 15;

    public void uploadMinio(String objectName) {
        try {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
//                    .httpClient(clientBuilder.build())
                    .build();

            String bucketName = "my-bucket";
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build());
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build());
            }

            File file = new File(CERTIFICATES_PATH + objectName);
            InputStream inputStream = new FileInputStream(file);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.length(), -1)
                            .build());

            System.out.println("File successfully uploaded to Minio!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}