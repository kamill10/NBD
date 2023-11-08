package p.lodz.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
