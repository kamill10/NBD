package p.lodz.Model.Type;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.util.UUID;

@CqlName("ClientType")
public class PremiumDeluxe extends ClientType{
    public PremiumDeluxe( ) {
        super(0.2, 2,"deluxe");
    }
}
