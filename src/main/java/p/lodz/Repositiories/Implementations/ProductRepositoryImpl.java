package p.lodz.Repositiories.Implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
import p.lodz.Model.Product;
import p.lodz.Repositiories.ProductRepository;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private final EntityManager em;
    public ProductRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Product saveProduct(Product product) {
        em.getTransaction().begin();
        if (product.getId() == null) {
            em.persist(product);
        } else {
            product = em.merge(product);
        }
        em.getTransaction().commit();
        return product;
    }

    @Override
    public Product archiveProduct(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            product.setArchived(true);
            em.merge(product);
        } else {
        }
        return product;
    }

    @Override
    public Product decrementNumberOfProducts(Long id) {
        Product product = em.find(Product.class,id);
        if (product != null) {
            int nop = product.getNumberOfProducts();
            if(nop > 0) {
                product.setNumberOfProducts(nop - 1);
                em.merge(product);
            } else {
                throw new RuntimeException("Number of products cannot be less than 0.");
            }
        }
        return product;
    }

    @Override
    public Product findProductById(Long id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> findAllProducts() {
        Query query = em.createQuery("SELECT p FROM Product p");
        return query.getResultList();
    }

}