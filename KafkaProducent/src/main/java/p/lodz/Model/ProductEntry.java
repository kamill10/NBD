package p.lodz.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ProductEntry {

    private Product product;
    private int quantity;

    public ProductEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getCost() {
        return (product.getBaseCost() - product.getDiscount()) * quantity;
    }
}
