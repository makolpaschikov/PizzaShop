package com.example.pizza_shop.service.product;

import com.example.pizza_shop.domain.product.Product;
import com.example.pizza_shop.domain.product.ProductType;
import com.example.pizza_shop.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

@Service
public class ProductService {
    private final ProductDAO productDAO;

    @Autowired
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public boolean addProduct(Product product, MultipartFile image) {
        Product savedProduct = productDAO.save(product);
        return ImageHandler.saveImage(image, savedProduct.getProductID(), savedProduct.getProductType());
    }
}
