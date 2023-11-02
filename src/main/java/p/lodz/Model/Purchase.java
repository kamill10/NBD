package p.lodz.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.MoreObjects.toStringHelper;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
    private Map<Product, Integer> products;

    public Purchase(Client client, Map<Product, Integer> products) {
        this.client = client;
        this.products = products;
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
                    @BsonProperty("products") Map<Product, Integer> products){
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
//        for(Product product : products) {
//            finalCost += product.getBaseCost() -
//                    product.getBaseCost() * product.getDiscount() -
//                    client.getClientDiscount() * product.getBaseCost();
//        }

    }
}