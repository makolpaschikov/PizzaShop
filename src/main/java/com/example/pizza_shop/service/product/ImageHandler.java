package com.example.pizza_shop.service.product;

import com.example.pizza_shop.domain.product.ProductType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;

public class ImageHandler {

    /**
     * Saves the file to directory 'resources/static/images'
     * @param image - saved image
     * @param productId - id of image product
     * @param type - type of product (for directory)
     * @return - true if the image was saved, else false
     */
    static public boolean saveImage(MultipartFile image, long productId, ProductType type) {
        try {
            String dirForTransfer = getNewImageName(image, productId, type);
            if (dirForTransfer == null) {
                return false;
            } else {
                image.transferTo(new File(dirForTransfer));
                return true;
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    static private String getNewImageName(MultipartFile image, long productId, ProductType type) {
        URL url = ImageHandler.class
                .getClassLoader()
                .getResource("static/image/" + type.name().toLowerCase(Locale.ROOT));
        return url == null
                ? null
                : url.getFile().replaceAll("build/resources/main", "src/main/resources")
                + File.separator
                + productId + getImageType(Objects.requireNonNull(image.getOriginalFilename()));
    }

    static private String getImageType(String ordinalName) {
        return ordinalName.substring(ordinalName.lastIndexOf("."));
    }
}

