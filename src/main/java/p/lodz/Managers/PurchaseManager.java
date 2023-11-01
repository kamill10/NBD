package p.lodz.Managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.validation.Validator;
import p.lodz.Exceptions.InvalidPurchaseException;
import p.lodz.Model.*;
import p.lodz.Repositiories.Implementations.ProductRepositoryImpl;
import p.lodz.Repositiories.Implementations.PurchaseRepositoryImpl;
import p.lodz.Repositiories.ProductRepository;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.Iterator;
import java.util.List;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private EntityManager em;

    public PurchaseManager(EntityManager em) {
        this.purchaseRepository = new PurchaseRepositoryImpl(em);
        this.productRepository = new ProductRepositoryImpl(em);
        this.em = em;
    }

    public Purchase getPurchase(Long id){
        return purchaseRepository.findPurchaseById(id);
    }

    public Purchase registerPurchase(Client customer, List<Product> products){
        em.getTransaction().begin();
        if (products.isEmpty()) {
            em.getTransaction().rollback();
            throw new InvalidPurchaseException("The order cannot be processed. Products list is empty.");
        }
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            Product lockedProduct = em.find(Product.class,product.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            if(lockedProduct.isArchived()) {
                iterator.remove();
            }
            productRepository.decrementNumberOfProducts(lockedProduct.getId());
            if (lockedProduct.getNumberOfProducts() == 0) {
                productRepository.archiveProduct(lockedProduct.getId());
            }
        }
        Purchase purchase = new Purchase(customer, products);
        purchase = purchaseRepository.savePurchase(purchase);
        em.getTransaction().commit();
        return purchase;
    }

    public Purchase registerPurchase(Client customer, Product product){
        em.getTransaction().begin();
        Product lockedProduct = em.find(Product.class,product.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        if(lockedProduct.isArchived()) {
            em.getTransaction().rollback();
            throw new InvalidPurchaseException("The order cannot be processed. Product is Archived.");
        } else {
            productRepository.decrementNumberOfProducts(lockedProduct.getId());
            if (lockedProduct.getNumberOfProducts() == 0) {
                productRepository.archiveProduct(lockedProduct.getId());
            }
        }
        Purchase purchase = new Purchase(customer,lockedProduct);
        purchase =  purchaseRepository.savePurchase(purchase);
        em.getTransaction().commit();
        return purchase;
    }

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAllPurchases();
    }

    public List<Purchase> getAllClientPurchases(Client client) {
        return purchaseRepository.findAllClientPurchases(client);
    }
}