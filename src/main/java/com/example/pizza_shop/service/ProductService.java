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

    /**
     * Saves the file to directory 'resources/static/images'
     * @param image - saved image
     * @return - true if the image was saved, else false
     */
    public boolean saveImage(MultipartFile image) {
        if (!image.isEmpty()) {
            createDirectory();
        } else {
            return false;
        }
        try {
            // TODO: replace file name with product id
            String dirForTransfer = getClass()
                    .getClassLoader()
                    .getResource("static/images")
                    .getFile()
                    .replaceAll("build/resources/main", "src/main/resources")
                    + File.separator + image.getOriginalFilename().replaceAll("Ngk3Z", "lala1");
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
