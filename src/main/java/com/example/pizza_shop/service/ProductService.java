package com.example.pizza_shop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ProductService {
    @Value("${image.directory}")
    private String imageDirPath;

    public boolean saveImage(MultipartFile image) {
        if (!image.isEmpty()) {
            createDirectory();
        } else {
            return false;
        }
        try {
            String dirForTransfer = getClass()
                    .getClassLoader()
                    .getResource("static/images")
                    .getFile()
                    .replaceAll("build/resources/main", "src/main/resources")
                    + File.separator + image.getOriginalFilename();
            image.transferTo(new File(dirForTransfer));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createDirectory() {
        File imageDirectory = new File(imageDirPath);
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }
    }

}
