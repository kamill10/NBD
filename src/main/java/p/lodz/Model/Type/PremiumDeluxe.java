package p.lodz.Model.Type;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

public class PremiumDeluxe extends ClientType{
    public PremiumDeluxe() {
        super(0.2, 2);
    }
}
