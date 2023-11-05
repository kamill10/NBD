package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.AbstractMongoRepository;
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.ClientTypeRepository;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;
import p.lodz.Repositiories.MongoImplementations.ClientTypeRepositoryMongoDB;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRepositoryTest {

    static AbstractMongoRepository repository = new AbstractMongoRepository();
    static MongoDatabase mongoDatabase = repository.getDatabase();
    static ClientRepository clientRepository = new ClientRepositoryMongoDB(mongoDatabase.getCollection("clients_test", Client.class),mongoDatabase.getCollection("types",ClientType.class));

    // Initialize test clients
    Client testClient1 = new Client(
            "Robert", "Lewandowski", new Address("Lodz1", "przykladow2a", "44A"), new Standard());
    Client testClient2 = new Client(
            "Konrad1", "koza1", new Address("Lodz1", "przykladow2a", "44A"), new Premium());


    @Test
    void saveClientTest() {
        assertEquals(clientRepository.saveClient(testClient1), testClient1);
    }

    @Test
    void deleteClientTest() {
        clientRepository.saveClient(testClient2);
        assertEquals(clientRepository.deleteClient(testClient2.getEntityId()), true);
    }

    @Test
    void findClientByIdTest() {
        Client client = clientRepository.saveClient(testClient2);
        assertTrue(clientRepository.findClientById(testClient2.getEntityId()).equals(client));
    }

    @Test
    void findAllClients(){
        assertEquals(clientRepository.findAllClients().size(), 2);
    }

    @AfterAll
    static void cleanDataBase() {
        // Remove the collection after tests
        //Document command = new Document("replSetStepDown", 60);
        //mongoDatabase.runCommand(command);
        assertEquals(clientRepository.findAllClients().size(), 2);
        mongoDatabase.getCollection("clients_test").drop();
        mongoDatabase.getCollection("types").drop();
    }
}
