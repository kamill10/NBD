package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;
import p.lodz.Model.Client;
import p.lodz.Repositiories.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientRepositoryMongoDB implements ClientRepository {

    private final MongoCollection<Client> clientsCollection;

    public ClientRepositoryMongoDB(MongoCollection<Client> clientsCollection) {
        this.clientsCollection = clientsCollection;
    }


    @Override
    public Client saveClient(Client client) {
        clientsCollection.insertOne(client);
        return client;
    }

    @Override
    public boolean deleteClient(UUID id) {
        Client deletedClient = clientsCollection.findOneAndDelete(Filters.eq("_id", id));
        return deletedClient != null;
    }

    @Override
    public Client findClientById(UUID id) {
        return  clientsCollection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public List<Client> findAllClients() {
        return clientsCollection.find().into(new ArrayList<>());
    }
}
