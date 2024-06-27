package hana.common.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@TypeInfo(name = "ImageUtils", description = "이미지 유틸리티")
@Component
@RequiredArgsConstructor
public class ImageUtils {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @MethodInfo(name = "createImage", description = "이미지를 저장합니다.")
    public String createImage(String directory, MultipartFile multipartFile, String name) {
        try {
            String fileName = directory + "/" + name;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(multipartFile.getSize());
            amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
            return amazonS3Client.getUrl("theyounghana", fileName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "이미지를 가져오지 못했습니다.";
        }
    }

    @MethodInfo(name = "createImages", description = "이미지들을 저장합니다.")
    public List<String> createImages(String directory, List<MultipartFile> multipartFiles) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            urls.add(
                    createImage(
                            directory,
                            multipartFile,
                            multipartFiles.indexOf(multipartFile) + ".png"));
        }
        return urls;
    }

    @MethodInfo(name = "deleteImagesByDirectory", description = "디렉토리 내 모든 이미지를 삭제합니다.")
    public void deleteImagesByDirectory(String directory) {
        amazonS3Client.deleteObject(bucket, directory);
    }
}
