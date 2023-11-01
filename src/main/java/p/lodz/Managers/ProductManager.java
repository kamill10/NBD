package p.lodz.Managers;


import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import p.lodz.Exceptions.InvalidValueException;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Repositiories.Implementations.ProductRepositoryImpl;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;
import java.util.Set;

public class ProductManager {
    private final ProductRepository productRepository;
    private final Validator validator;

    public ProductManager(EntityManager em, Validator validator) {
        this.productRepository = new ProductRepositoryImpl(em);
        this.validator = validator;
    }

    public Product getProduct(Long id){
        return productRepository.findProductById(id);
    }

    public Product registerProduct(String productName, double baseCost, int numberOfProducts, String desciption){
        Product product = new Product(productName, baseCost, numberOfProducts, desciption);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<Product> violation : violations) {
                throw new InvalidValueException(violation.getMessage());
            }
        }
        return productRepository.saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

}
