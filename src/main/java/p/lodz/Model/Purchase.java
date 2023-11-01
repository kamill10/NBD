package p.lodz.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.MoreObjects.toStringHelper;

@Getter
@Entity
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "final_cost")
    @Min(value = 0)
    private double finalCost;

    @ManyToOne
    private Client client;

    @ManyToMany
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
                .add("purchaseId", id)
                .add("purchaseDate", purchaseDate)
                .add("deliveryDate", deliveryDate)
                .add("finalCost", finalCost)
                .add("client", client)
                .add("product", products)
                .toString();
    }
}