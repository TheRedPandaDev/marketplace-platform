package com.realthomasmiles.marketplace.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary = Singleton.getCloudinary();

    @Override
    public String upload(MultipartFile file) {
        try {
            @SuppressWarnings("rawtypes") Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            return getCloudUrl(uploadResult.get("public_id").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getCloudUrl(String publicId) {
        return cloudinary.url().secure(true).format("jpg")
                .publicId(publicId)
                .generate();
    }

}
