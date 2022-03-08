package com.sprinterproject.api.service;

import com.sprinterproject.api.exception.ResourceNotFoundException;
import com.sprinterproject.api.model.Product;
import com.sprinterproject.api.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl  implements ProductService{
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository repository;

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> productDb = this.repository.findById(product.getId());
        if (productDb.isPresent()) {
            Product productUpdate = productDb.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setPrice(product.getPrice());
            repository.save(productUpdate);
            return productUpdate;
        }else {
            logger.error("Product with id: " + product.getId() + " not found");
            throw new ResourceNotFoundException("Product with id: " + product.getId() + " not found");
        }
    }

    @Override
    public List<Product> getAllProduct() {
        return this.repository.findAll();
    }

    @Override
    public Product getProductById(long productId) {
        Optional<Product> productDb = this.repository.findById(productId);
        if (productDb.isPresent()) {
            return productDb.get();
        }else {
            logger.error("Product with id: " + productId + " not found");
            throw new ResourceNotFoundException("Product with id: " + productId + " not found");
        }
    }

    @Override
    public void deleteProduct(long productId) {
        Optional<Product> productDb = this.repository.findById(productId);
        if (productDb.isPresent()) {
            this.repository.delete(productDb.get());
        }else {
            logger.error("Product with id: " + productId + " not found");
            throw new ResourceNotFoundException("Product with id: " + productId + " not found");
        }
    }
}
