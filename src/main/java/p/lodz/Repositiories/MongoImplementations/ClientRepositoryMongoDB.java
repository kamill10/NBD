package p.lodz.Repositiories.MongoImplementations;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.checkerframework.checker.units.qual.C;
import p.lodz.Model.Client;
import p.lodz.Repositiories.ClientRepository;

import java.util.List;

public class ClientRepositoryMongoDB implements ClientRepository {

    private MongoCollection<Client> clientsCollection;

    public ClientRepositoryMongoDB(MongoCollection<Client> clientsCollection) {
        this.clientsCollection = clientsCollection;
    }
    @Override
    public Client saveClient(Client client) {

        clientsCollection.insertOne(client);
        return client;
    }

    @Override
    public Client archiveClient(Long id) {
        return null;
    }

    @Override
    public Client findClientById(Long id) {
        return null;
    }

    @Override
    public List<Client> findAllClients(boolean archived) {
        return null;
    }
}
