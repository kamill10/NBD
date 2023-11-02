package p.lodz.Model.Type;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = "_clazz", value = "standard")
public class Standard extends ClientType{
    public Standard() {
        super(0, 0);
    }
}
