package p.lodz.Managers;

import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;

import java.util.List;

public class ClientManager {
    private final ClientRepository clientRepository;

    public ClientManager(MongoCollection<Client> collection) {
        this.clientRepository = new ClientRepositoryMongoDB(collection);
    }

    public Client getClient(ObjectId id){
        return clientRepository.findClientById(id);
    }

    public Client registerClient(String firstName, String lastName, String city, String street, String number, ClientType clientType) {
        Address address = new Address(city, street, number);
        Client client = new Client(firstName, lastName, address, clientType);
        return clientRepository.saveClient(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAllClients();
    }
}
