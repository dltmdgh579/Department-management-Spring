package ministryofeducation.sideprojectspring.config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaveToLocal {

    private final String uuid = UUID.randomUUID().toString();

    public String saveProfileImageFile(String name, MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) return null;

        String dir = "/var/images/personnel/";
        String path = name + "_" + uuid;
        String savedPath = dir + path;

        String contentType = multipartFile.getContentType();
        if(ObjectUtils.isEmpty(contentType)) throw new IllegalArgumentException("파일 확장명이 올바르지 않습니다.");

        if(contentType.contains("image/jpeg"))
            savedPath += ".jpg";
        else if(contentType.contains("image/png"))
            savedPath += ".png";
        else throw new IllegalArgumentException("지원하지 않는 확장자입니다.");

        File file = new File(savedPath);
        if(!file.exists()) file.mkdirs();

        multipartFile.transferTo(file);

        return path;
    }

}
