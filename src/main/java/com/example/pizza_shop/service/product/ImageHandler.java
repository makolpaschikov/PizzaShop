package com.example.pizza_shop.service.product;

import com.example.pizza_shop.domain.product.ProductType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class ImageHandler {

    /*-------------- Public --------------*/

    /**
     * Saves the file to directory 'resources/static/images'
     * @param image - saved image
     * @param productId - id of image product
     * @param type - type of product (for directory)
     * @return - true if the image was saved, else false
     */
    static public boolean saveImage(MultipartFile image, long productId, ProductType type) {
        try {
            String dirForTransfer = getImageName(productId, type);
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

    /**
     * Deleted the file from directory 'resources/static/images'
     * @param imageFilename - name of image (id of image product)
     * @param type - type of product (for directory)
     * @return - true if the image was deleted, else false
     */
    static public boolean deleteImage(Long imageFilename, ProductType type) {
        String imageDir = getImageName(imageFilename, type);
        return imageDir != null && new File(imageDir).delete();
    }

    /*-------------- Private --------------*/

    static private String getImageName(Long name, ProductType type) {
        URL url = ImageHandler.class
                .getClassLoader()
                .getResource("static/image/" + type.name().toLowerCase(Locale.ROOT));
        return url == null
                ? null
                : url.getFile().replaceAll("build/resources/main", "src/main/resources")
                + File.separator + name + ".jpeg";
    }

}

