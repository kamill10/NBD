package p.lodz.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;


    @Column(name = "product_name")
    @NotNull
    private String productName;
    @Column(name = "base_cost")
    @NotNull
    private double baseCost;
    @Column(name = "discount")
    @NotNull
    private double discount;
    @Column(name = "archived")
    private boolean archived = false;
    @Column(name = "nuber_of_products")
    @NotNull
    @Min(value = 0, message = "Number of products cannot be less than 0.")
    private int numberOfProducts;
    @Column(name = "description")
    @Size(max = 100, message = "Description cannot be longer than 100 characters.")
    private String description;

    public Product(String productName, double baseCost, int numberOfProducts, String description) {
        this.productName = productName;
        this.baseCost = baseCost;
        this.numberOfProducts = numberOfProducts;
        this.description = description;
    }
}
