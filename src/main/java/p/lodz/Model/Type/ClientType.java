package p.lodz.Model.Type;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;



@Getter
@Setter
@NoArgsConstructor
public abstract class ClientType {

    @BsonProperty("clientdiscount")
    private double clientDiscount;

    @BsonProperty("shorterdeliverytime")
    private int shorterDeliveryTime;

    @BsonCreator
    public ClientType(@BsonProperty("clientdiscount") double clientDiscount, @BsonProperty("shorterdeliverytime") int shorterDeliveryTime) {
        this.clientDiscount = clientDiscount;
        this.shorterDeliveryTime = shorterDeliveryTime;
    }

}
