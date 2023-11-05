package p.lodz.Model;

import com.mongodb.MongoCommandException;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import lombok.Getter;
import org.bson.Document;
import p.lodz.Managers.ClientManager;
import p.lodz.Managers.ProductManager;
import p.lodz.Managers.PurchaseManager;
import p.lodz.Repositiories.AbstractMongoRepository;

@Getter
public class Shop implements AutoCloseable{
    private final ClientManager clientManager;
    private final ProductManager productManager;
    private final PurchaseManager purchaseManager;
    private final AbstractMongoRepository repository;

    public Shop() {
        repository = new AbstractMongoRepository();
        createPurchaseCollectionWithValidations();
        createProductCollectionWithValidations();
        clientManager = new ClientManager(repository.getDatabase().getCollection("clients", Client.class));
        productManager = new ProductManager(repository.getDatabase().getCollection("products", Product.class));
        purchaseManager = new PurchaseManager(repository.getDatabase().getCollection("purchases", Purchase.class), repository.getDatabase().getCollection("products", Product.class), repository);
    }

    private void createPurchaseCollectionWithValidations() {
        Document validator = Document.parse("""
                            {
                                $jsonSchema:{
                                    "bsonType": "object",
                                    "required": ["client", "delivery_date", "final_cost", "products", "purchase_date"],
                                    "properties": {
                                        "client": {
                                            "bsonType": "object"
                                        },
                                        "delivery_date": {
                                            "bsonType": "date"
                                        },
                                        "final_cost": {
                                            "bsonType": "double",
                                            "minimum": 0
                                        },
                                        "products": {
                                            "bsonType": "array"
                                        },
                                        "purchase_date": {
                                            "bsonType": "date"
                                        }
                                    }
                                }
                            }
                        """);
        try {
            ValidationOptions validationOptions = new ValidationOptions().validator(validator);
            CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
            repository.getDatabase().createCollection("purchases", createCollectionOptions);
        } catch (MongoCommandException ignored) {
            Document command = new Document("collMod", "purchases").append("validator", validator);
            repository.getDatabase().runCommand(command);
        }

    }

    private void createProductCollectionWithValidations() {
        Document validator = Document.parse("""
                            {
                                $jsonSchema:{
                                    "bsonType": "object",
                                    "required": ["archived", "base_cost", "description", "discount", "number_of_products", "product_name"],
                                    "properties": {
                                        "archived": {
                                            "bsonType": "bool"
                                        },
                                        "base_cost": {
                                            "bsonType": "double"
                                        },
                                        "description": {
                                            "bsonType": "string"
                                        },
                                        "discount": {
                                            "bsonType": "double"
                                        },
                                        "number_of_products": {
                                            "bsonType": "int",
                                            "minimum": 0
                                        },
                                        "product_name": {
                                            "bsonType": "string"
                                        }
                                    }
                                }
                            }
                        """);
        try {
            ValidationOptions validationOptions = new ValidationOptions().validator(validator);
            CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions().validationOptions(validationOptions);
            repository.getDatabase().createCollection("products", createCollectionOptions);
        } catch (MongoCommandException ignored) {
            Document command = new Document("collMod", "products").append("validator", validator);
            repository.getDatabase().runCommand(command);
        }

    }

    @Override
    public void close() throws Exception {
        repository.close();
    }
}
