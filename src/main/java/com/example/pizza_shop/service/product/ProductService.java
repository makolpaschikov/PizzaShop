package com.example.pizza_shop.service.product;

import com.example.pizza_shop.domain.product.Product;
import com.example.pizza_shop.domain.product.ProductType;
import com.example.pizza_shop.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    public List<Product> getProducts() {
        return (List<Product>) productDAO.findAll();
    }

    public List<Product> getProducts(ProductType type) {
        return productDAO.getAllByProductType(type);
    }

    public void deleteProduct(Long id, ProductType type) {
        productDAO.deleteById(id);
        ImageHandler.deleteImage(id, type);
    }

    public void deleteAll() {
        for (Product product : getProducts()) {
            deleteProduct(product.getProductID(), product.getProductType());
        }
    }
}
