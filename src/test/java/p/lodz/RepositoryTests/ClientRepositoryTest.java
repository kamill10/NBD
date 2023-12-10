package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.CassandraImplementations.ClientRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRepositoryTest {
/*
    static AbstractMongoRepository repository = new AbstractMongoRepository();
    static MongoDatabase mongoDatabase = repository.getDatabase();
    static p.lodz.Repositiories.ClientRepository clientRepository = new ClientRepository(mongoDatabase.getCollection("clients_test", Client.class));

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
        assertTrue(clientRepository.deleteClient(testClient2.getEntityId()));
    }

    @Test
    void findClientByIdTest() {
        Client client = clientRepository.saveClient(testClient2);
        assertEquals(clientRepository.findClientById(testClient2.getEntityId()), client);
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
    } */
}
