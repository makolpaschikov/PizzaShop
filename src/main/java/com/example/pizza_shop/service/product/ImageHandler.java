package com.example.pizza_shop.service.product;

import com.example.pizza_shop.domain.product.ProductType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ImageHandler {

    static private final String DIRECTORY = System.getProperty("user.dir");

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
            String dirForTransfer = createImgName(productId, type);
            image.transferTo(new File(dirForTransfer));
            return true;
        } catch (IOException e) {
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
        String imageDir = createImgName(imageFilename, type);
        return new File(imageDir).delete();
    }

    /*-------------- Private --------------*/

    static private String createImgName(Long name, ProductType type) {
        String dir = DIRECTORY + File.separator + "data/images/" + type.name().toLowerCase(Locale.ROOT);
        directoryValidating(new File(dir));
        return dir + File.separator + name + ".jpeg";
    }

    static private void directoryValidating(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

}

