package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.checkerframework.checker.units.qual.C;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.ClientTypeRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMongoDB implements ClientRepository {

    private MongoCollection<Client> clientsCollection;
    private MongoCollection<ClientType> clientTypeRepository;


    public ClientRepositoryMongoDB(MongoCollection<Client> clientsCollection) {
        this.clientsCollection = clientsCollection;
    }
    public ClientRepositoryMongoDB(MongoCollection<Client> clientsCollection,MongoCollection<ClientType>clientTypes) {
        this.clientsCollection = clientsCollection;
        clientTypeRepository = clientTypes;
    }

    @Override
    public Client saveClient(Client client) {
        clientsCollection.insertOne(client);
        if(clientTypeRepository != null) {
            clientTypeRepository.insertOne(client.getClientType());
        }
        return client;
    }

    @Override
    public boolean deleteClient(ObjectId id) {
        Client deletedClient = clientsCollection.findOneAndDelete(Filters.eq("_id", id));
        return deletedClient != null;
    }

    @Override
    public Client findClientById(ObjectId id) {
        return clientsCollection.findOneAndDelete(Filters.eq("_id", id));
    }

    @Override
    public List<Client> findAllClients() {
        return clientsCollection.find().into(new ArrayList<>());
    }
}
