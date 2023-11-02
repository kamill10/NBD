package p.lodz.Model.Type;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = "_clazz", value = "premium_deluxe")
public class PremiumDeluxe extends ClientType{
    public PremiumDeluxe() {
        super(0.2, 2);
    }
}
