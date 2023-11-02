package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.ClientTypeRepository;

public class ClientTypeRepositoryMongoDB implements ClientTypeRepository {

    private final MongoCollection<ClientType> mongoCollection;

    public ClientTypeRepositoryMongoDB(MongoCollection<ClientType> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }
    @Override
    public ClientType saveClientType(ClientType clientType) {
        mongoCollection.insertOne(clientType);
        return clientType;
    }

    @Override
    public ClientType getClientType(String type) {
        return mongoCollection.find(Filters.eq("_clazz", type)).first();
    }
}
