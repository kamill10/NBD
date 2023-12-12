package p.lodz.Model.Type;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.UUID;

@Entity
@CqlName("clienttype")
public class ClientType {

    @PartitionKey
    @CqlName("id")
    protected UUID id;

    public UUID getId() {
        return id;
    }

    @CqlName("client_discount")
    protected double clientDiscount;

   @CqlName("discriminator")
   protected String discriminator;
    @CqlName("shorter_delivery_time")
    protected int shorterDeliveryTime;

    public ClientType(double clientDiscount, int shorterDeliveryTime,String discriminator) {
        this.id = UUID.randomUUID();
        this.clientDiscount = clientDiscount;
        this.shorterDeliveryTime = shorterDeliveryTime;
        this.discriminator = discriminator;
    }
    public ClientType(){};

    public String getDiscriminator() {
        return discriminator;
    }

    public double getClientDiscount() {
        return clientDiscount;
    }

    public void setClientDiscount(double clientDiscount) {
        this.clientDiscount = clientDiscount;
    }

    public int getShorterDeliveryTime() {
        return shorterDeliveryTime;
    }

    public void setShorterDeliveryTime(int shorterDeliveryTime) {
        this.shorterDeliveryTime = shorterDeliveryTime;
    }
}
