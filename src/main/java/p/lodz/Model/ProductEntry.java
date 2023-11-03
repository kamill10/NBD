package p.lodz.Model;

import lombok.Getter;

@Getter
public class ProductEntry {
    private final Product product;
    private final int quantity;

    public ProductEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getCost() {
        return (product.getBaseCost() - product.getDiscount()) * quantity;
    }
}
