package p.lodz.Model.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
public abstract class ClientType {
    @Override
    public String toString() {
        return "ClientType{" +
                "clientDiscount=" + clientDiscount +
                ", shorterDeliveryTime=" + shorterDeliveryTime +
                '}';
    }

    @BsonProperty("client_discount")
    protected double clientDiscount;

    @BsonProperty("shorter_delivery_time")
    protected int shorterDeliveryTime;

    @BsonCreator
    public ClientType(@BsonProperty("client_discount") double clientDiscount,
                      @BsonProperty("shorter_delivery_time") int shorterDeliveryTime) {
        this.clientDiscount = clientDiscount;
        this.shorterDeliveryTime = shorterDeliveryTime;
    }

}
