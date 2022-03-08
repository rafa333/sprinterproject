package com.sprinterproject.api;

import com.sprinterproject.api.exception.ResourceNotFoundException;
import com.sprinterproject.api.model.Product;
import com.sprinterproject.api.repository.ProductRepository;
import com.sprinterproject.api.service.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiUnitTests {


    @Autowired
    ProductService service;

    @Autowired
    ProductRepository repository;

    @Before
    public void setUp () {
        Product product = new Product();
        product.setId(1l);
        product.setName("Zapatillas Nike");
        product.setDescription("Nike performance description");
        product.setPrice(BigDecimal.valueOf(100));
        List<Product> productList = service.getAllProduct();
        productList.add(product);
    }


    @Test
    @Order(1)
    public void createProductTest () {
        Product product = new Product();
        product.setId(2l);
        product.setName("product1");
        product.setDescription("Product description");
        product.setPrice(BigDecimal.valueOf(20));

        Product savedProduct = service.createProduct(product);

        assertNotNull(savedProduct);
    }

    @Test
    @Order(2)
    public void getAllProductTest () {
        List<Product> productList = service.getAllProduct();

        assertNotNull(productList);

    }

    @Test
    @Order(3)
    public void getProductByIdTest () {
        long id = 1L;

        Product product = service.getProductById(id);

        assertEquals(product.getId(), id);
    }

    @Test
    @Order(4)
    public void getProductByIdNotExistTest () throws ResourceNotFoundException {
        long id= 2L;
        try {
            Product product = service.getProductById(id);
            fail("Should throw ResourceNotFoundException");
        }catch (Exception e) {
            assertEquals(e.getMessage(), "Product with id: " + id + " not found");
        }
    }

    @Test
    @Order(5)
    public void updateProductTest () {
        long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setName("product2");
        product.setDescription("Product description dummy");
        product.setPrice(BigDecimal.valueOf(30));

        service.updateProduct(product);

        Product updatedProduct = service.getProductById(id);

        assertEquals(updatedProduct.getName(), product.getName());
    }

    @Test
    @Order(6)
    public void updateProductFailedTest () throws ResourceNotFoundException {
        long id = 2L;
        try {
            Product product = new Product();
            product.setId(id);
            product.setName("product2");
            product.setDescription("Product description dummy");
            product.setPrice(BigDecimal.valueOf(30));

            service.updateProduct(product);

            fail("Should throw ResourceNotFoundException");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Product with id: " + id + " not found");
        }
    }

    @Test
    @Order(7)
    public void deleteProductTest () {
        long id = 1L;

        boolean isExistBeforeDelete = repository.findById(id).isPresent();

        service.deleteProduct(id);

        boolean notExistAfterDelete = repository.findById(id).isPresent();


        assertTrue(isExistBeforeDelete);
        assertFalse(notExistAfterDelete);

    }

    @Test
    @Order(8)
    public void deleteProductFailedTest () throws ResourceNotFoundException {

        long id = 2L;

        try {
            service.deleteProduct(id);

            fail("Should throw ResourceNotFoundException");

        }catch (Exception e) {
            assertEquals(e.getMessage(), "Product with id: " + id + " not found");
        }
    }
}
