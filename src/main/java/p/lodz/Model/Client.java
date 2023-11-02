package p.lodz.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import p.lodz.Model.Type.ClientType;

@Getter
@Setter
@NoArgsConstructor
public class Client extends AbstractEntity {

    @BsonProperty("firstname")
    private String firstName;
    @BsonProperty("lastname")
    private String lastName;
    @BsonProperty("address")
    Address address;
    @BsonProperty("clienttype")
    ClientType clientType;

    @BsonProperty("moneyspent")
    private double moneySpent = 0;

    public Client( String firstName, String lastName, Address address, ClientType clientType) {
        super(new ObjectId());
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
    public Client(@BsonProperty("_id") ObjectId entityId,
                  @BsonProperty("firstname") String firstName,
                  @BsonProperty("lastname") String lastName,
                  @BsonProperty("clienttype") ClientType clientType,
                  @BsonProperty("moneyspent") double money,
                  @BsonProperty("address") Address address) {
        super(entityId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientType = clientType;
        this.moneySpent = money;
        this.address = address;
    }


}
