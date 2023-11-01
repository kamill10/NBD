package p.lodz.Repositiories.Implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;
import p.lodz.Repositiories.PurchaseRepository;

import java.util.List;

public class PurchaseRepositoryImpl implements PurchaseRepository {
    private final EntityManager em;
    public PurchaseRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Purchase findPurchaseById(Long id) {
        return em.find(Purchase.class, id);
    }

    @Override
    public List<Purchase> findAllPurchases() {
        Query query = em.createQuery("SELECT p FROM Purchase p");
        return query.getResultList();
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        if(purchase.getId() == null) em.persist(purchase);
        else purchase = em.merge(purchase);
        return purchase;
    }

    @Override
    public List<Purchase> findAllClientPurchases(Client client) {
        TypedQuery<Purchase> query = em.createQuery("SELECT p FROM Purchase p WHERE p.client = :client", Purchase.class);
        query.setParameter("client", client);
        return query.getResultList();
    }

}