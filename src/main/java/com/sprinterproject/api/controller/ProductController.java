package com.sprinterproject.api.controller;

import com.sprinterproject.api.model.Product;
import com.sprinterproject.api.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        logger.info("Listando los productos...");
        return ResponseEntity.ok().body(service.getAllProduct());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        logger.info("Listando producto...");
        return ResponseEntity.ok().body(service.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        logger.info("Creando producto...");
        return ResponseEntity.ok().body(this.service.createProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        logger.info("Actualizando producto...");
        product.setId(id);
        return ResponseEntity.ok().body(this.service.updateProduct(product));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable long id) {
        logger.info("Eliminando producto...");
        this.service.deleteProduct(id);
        return HttpStatus.OK;

    }
}
