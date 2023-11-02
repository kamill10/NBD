package p.lodz.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;

@Getter
@Setter
@NoArgsConstructor
public class Purchase extends AbstractEntity{

    @BsonProperty("purchasedate")
    private LocalDate purchaseDate;

    @BsonProperty("deliverydate")
    private LocalDate deliveryDate;

    @BsonProperty("finalcost")
    private double finalCost;

    @BsonProperty("client")
    private Client client;

    @BsonProperty("products")
    private List<Product> products;

    public Purchase(Client client, List<Product> products) {
        this.client = client;
        this.products = products;
        purchaseDate = LocalDate.now();
        setDeliveryTime();
        setFinalCost();
        client.addMoneySpent(finalCost);
    }

    public Purchase(Client client, Product product) {
        this.client = client;
        this.products = new ArrayList<>();
        this.products.add(product);
        purchaseDate = LocalDate.now();
        setDeliveryTime();
        setFinalCost();
        client.addMoneySpent(finalCost);
    }

    @BsonCreator
    public Purchase(@BsonProperty("purchasedate") LocalDate purchaseDate,
                    @BsonProperty("deliverydate") LocalDate deliveryDate,
                    @BsonProperty("finalcost") double finalCost,
                    @BsonProperty("client") Client client,
                    @BsonProperty("products") List<Product> products){
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.finalCost = finalCost;
        this.client = client;
        this.products = products;

    }

    private void setDeliveryTime(){
        deliveryDate = purchaseDate.plusDays(3 - client.getClientShorterDeliveryTime());
    }

    private void setFinalCost(){
        for(Product product : products) {
            finalCost += product.getBaseCost() -
                    product.getBaseCost() * product.getDiscount() -
                    client.getClientDiscount() * product.getBaseCost();
        }
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("purchaseId", getEntityId())
                .add("purchaseDate", purchaseDate)
                .add("deliveryDate", deliveryDate)
                .add("finalCost", finalCost)
                .add("client", client)
                .add("product", products)
                .toString();
    }
}