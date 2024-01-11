package p.lodz.Model.Type;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
public class Premium extends ClientType {
    public Premium() {
        super(0.1, 1);
    }
}
