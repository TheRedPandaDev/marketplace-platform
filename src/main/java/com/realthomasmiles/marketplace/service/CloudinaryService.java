package com.realthomasmiles.marketplace.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String upload(MultipartFile file);

}
