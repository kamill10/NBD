package p.lodz.Model.Type;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.util.UUID;

@CqlName("ClientType")
public class Standard extends ClientType{
    public Standard() {
        super(0, 0,"standard");
    }
}
