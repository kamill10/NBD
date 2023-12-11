package p.lodz.Model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;


import java.util.UUID;


@Entity
@CqlName("products")
public class Product {

    @PartitionKey
    @CqlName("id")
   private UUID id;
    @ClusteringColumn
    @CqlName("base_cost")
    private double baseCost;

    @CqlName("product_name")
    private String  productName;

    @CqlName("discount")
    private double discount;

    @CqlName("archived")
    private boolean archived = false;

    @CqlName("number_of_products")
    private int numberOfProducts;
    @CqlName("description")
    private String description;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", baseCost=" + baseCost +
                ", productName='" + productName + '\'' +
                ", discount=" + discount +
                ", archived=" + archived +
                ", numberOfProducts=" + numberOfProducts +
                ", description='" + description + '\'' +
                '}';
    }

    public Product(String productName,
                   double baseCost,
                   int numberOfProducts,
                   String description) {
        id= UUID.randomUUID();
        this.productName = productName;
        this.baseCost = baseCost;
        this.numberOfProducts = numberOfProducts;
        this.description = description;
    }


    public Product() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
