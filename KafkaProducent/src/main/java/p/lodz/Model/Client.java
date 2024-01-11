package p.lodz.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import p.lodz.Model.Type.ClientType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client extends AbstractEntity {

    @BsonProperty("first_name")
    private String firstName;
    @BsonProperty("last_name")
    private String lastName;
    @BsonProperty("address")
    Address address;
    @BsonProperty("client_type")
    ClientType clientType;

    @BsonProperty("money_spent")
    private double moneySpent = 0;

    public Client( String firstName, String lastName, Address address, ClientType clientType) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
    }

    @BsonIgnore
    public double getClientDiscount(){
        return clientType.getClientDiscount();
    }
    @BsonIgnore
    public int getClientShorterDeliveryTime(){
        return clientType.getShorterDeliveryTime();
    }

    public void addMoneySpent(double value){
        moneySpent += value;
    }




    @BsonCreator
    public Client(@BsonProperty("_id") UUID entityId,
                  @BsonProperty("first_name") String firstName,
                  @BsonProperty("last_name") String lastName,
                  @BsonProperty("client_type") ClientType clientType,
                  @BsonProperty("money_spent") double money,
                  @BsonProperty("address") Address address) {
        super(entityId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientType = clientType;
        this.moneySpent = money;
        this.address = address;
    }
}
