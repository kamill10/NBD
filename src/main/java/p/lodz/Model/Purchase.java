package p.lodz.Model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.*;




public class Purchase {


    private UUID id;


    private LocalDate purchaseDate;


    private LocalDate deliveryDate;


    private double finalCost;


    private UUID client;


    private UUID products;

    public Purchase(Client client, Product product) {
        this.id = UUID.randomUUID();
        this.client = client.getId();
        this.products = product.getId();
        purchaseDate = LocalDate.now();
        setDeliveryTime();
        this.finalCost = product.getBaseCost();
        client.addMoneySpent(finalCost);
    }
    public Purchase(){};


    private void setDeliveryTime(){
        deliveryDate = purchaseDate.plusDays(3 );
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public UUID getClient() {
        return client;
    }

    public void setClient(UUID client) {
        this.client = client;
    }

    public UUID getProducts() {
        return products;
    }

    public void setProducts(UUID products) {
        this.products = products;
    }

    public UUID getId() {
        return id;
    }



    public void setId(UUID id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", deliveryDate=" + deliveryDate +
                ", finalCost=" + finalCost +
                ", client=" + client +
                ", products=" + products +
                '}';
    }


}