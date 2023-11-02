package p.lodz.Mappers;

import org.bson.Document;
import p.lodz.Model.Product;

public class ProductMapper {
    public static Product fromMongoProduct(Document product) {
        return new Product(
                product.getObjectId("_id"),
                product.getString("product_name"),
                product.getDouble("base_cost"),
                product.getDouble("discount"),
                product.getBoolean("archived"),
                product.getInteger("number_of_products"),
                product.getString("description")
        );
    }

    public static Document toMongoProduct(Product product) {
        return new Document("_id", product.getEntityId())
                .append("product_name", product.getProductName())
                .append("base_cost", product.getBaseCost())
                .append("discount", product.getDiscount())
                .append("archived", product.isArchived())
                .append("number_of_products", product.getNumberOfProducts())
                .append("description", product.getDescription());
    }
}
