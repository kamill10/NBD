package p.lodz.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;

@Getter
@Setter
@NoArgsConstructor
public class Purchase extends AbstractEntity{

    @BsonProperty("purchase_date")
    private LocalDate purchaseDate;

    @BsonProperty("delivery_date")
    private LocalDate deliveryDate;

    @BsonProperty("final_cost")
    private double finalCost;

    @BsonProperty("client")
    private Client client;

    @BsonProperty("products")
    private List<ProductEntry> products;

    public Purchase(Client client, List<ProductEntry> products) {
        super(UUID.randomUUID());
        this.client = client;
        this.products = products;
        purchaseDate = LocalDate.now();
        setDeliveryTime();
        setFinalCost();
        client.addMoneySpent(finalCost);
    }
    public Purchase(Client client, ProductEntry product) {
        super(UUID.randomUUID());
        this.client = client;
        this.products = new ArrayList<>(Arrays.asList(product));
        purchaseDate = LocalDate.now();
        setDeliveryTime();
        setFinalCost();
        client.addMoneySpent(finalCost);
    }
    public UUID getID(){
        return super.getId();
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + getId()+
                "purchaseDate=" + purchaseDate +
                ", deliveryDate=" + deliveryDate +
                ", finalCost=" + finalCost +
                ", client=" + client +
                ", products=" + products +
                '}';
    }

    @BsonCreator
    public Purchase(@BsonProperty("purchase_date") LocalDate purchaseDate,
                    @BsonProperty("delivery_date") LocalDate deliveryDate,
                    @BsonProperty("final_cost") double finalCost,
                    @BsonProperty("client") Client client,
                    @BsonProperty("products") List<ProductEntry> products){
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.finalCost = finalCost;
        this.client = client;
        this.products = products;

    }

    private void setDeliveryTime(){
        deliveryDate = purchaseDate.plusDays(3 - client.getClientShorterDeliveryTime());
    }

    private void setFinalCost() {
        products.forEach(e -> {
            finalCost += e.getCost();
        });

    }
}