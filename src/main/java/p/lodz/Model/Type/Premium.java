package p.lodz.Model.Type;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.util.UUID;

@CqlName("ClientType")
public class Premium extends ClientType {
    public Premium( ) {
        super(0.1, 1,"premium");
    }
}
