package p.lodz;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;

public class ClientTypeCodec implements Codec<ClientType> {

    private final CodecRegistry registry;

    public ClientTypeCodec(CodecRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void encode(BsonWriter writer, ClientType clientType, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("_clazz", clientType.getClass().getSimpleName().toLowerCase());
        writer.writeDouble("clientdiscount", clientType.getClientDiscount());
        writer.writeInt32("shorterdeliverytime", clientType.getShorterDeliveryTime());
        writer.writeEndDocument();
    }

    @Override
    public ClientType decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String type = reader.readString();
        double clientDiscount = reader.readDouble();
        int shorterDeliveryTime = reader.readInt32();
        reader.readEndDocument();

        if (type.equals("standard")) {
            return new Standard();
        } else if (type.equals("premium")) {
            return new Premium();
        } else {
            return new PremiumDeluxe();
        }
    }

    @Override
    public Class<ClientType> getEncoderClass() {
        return ClientType.class;
    }
}
