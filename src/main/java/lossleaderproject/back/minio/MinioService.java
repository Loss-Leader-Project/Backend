package lossleaderproject.back.minio;
import io.minio.*;
import io.minio.errors.MinioException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {
    @Value("${minio.end_point}")
    private String endPoint;
    @Value("${minio.access_key}")
    private String accessKey;
    @Value("${minio.secret_key}")
    private String secretKey;
    public void imageUpload(String bucket,String objectName, InputStream stream) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            InputStream newStream = ImageUtils.resize(stream,1200,900);


            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endPoint)
                            .credentials(accessKey,secretKey)
                            .build();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                // Make a new bucket if not exist
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(newStream, -1, 10485760)
                    .contentType("image/jpg")
                    .build());
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
    public void imageRemove(String bucket,String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endPoint)
                            .credentials(accessKey,secretKey)
                            .build();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                // Make a new bucket if not exist
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}

