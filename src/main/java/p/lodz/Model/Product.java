package p.lodz.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString
public class Product extends AbstractEntity {

//    @BsonProperty("_id")
//    private ObjectId id;
    @BsonProperty("product_name")
    private String productName;
    @BsonProperty("base_cost")
    private double baseCost;
    @BsonProperty("discount")
    private double discount;
    @BsonProperty("archived")
    private boolean archived = false;
    @BsonProperty("number_of_products")
    private int numberOfProducts;
    @BsonProperty("description")
    private String description;

    @BsonCreator
    public Product(@BsonProperty("_id") ObjectId id,
                   @BsonProperty("product_name") String productName,
                   @BsonProperty("base_cost") double baseCost,
                   @BsonProperty("discount") double discount,
                   @BsonProperty("archived") boolean archived,
                   @BsonProperty("number_of_products") int numberOfProducts,
                   @BsonProperty("description") String description) {
        super(id);
        this.productName = productName;
        this.baseCost = baseCost;
        this.discount = discount;
        this.archived = archived;
        this.numberOfProducts = numberOfProducts;
        this.description = description;
    }

    public Product(String productName,
                   double baseCost,
                   int numberOfProducts,
                   String description) {
        super(new ObjectId());
        this.productName = productName;
        this.baseCost = baseCost;
        this.numberOfProducts = numberOfProducts;
        this.description = description;
    }
}
