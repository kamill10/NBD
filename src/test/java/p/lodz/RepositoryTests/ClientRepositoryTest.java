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
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.AbstractMongoRepository;
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.ClientTypeRepository;
import p.lodz.Repositiories.MongoImplementations.ClientRepositoryMongoDB;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {

    Client testClient1 = new Client(
            "Robert", "Lewandowski", new Address("Lodz1", "przykladow2a", "44A"), new Standard());
    Client testClient2 = new Client(
            "Jacek", "Bąk", new Address("Lodz1", "przykladow2a", "44A"), new Premium());
    static AbstractMongoRepository repository = new AbstractMongoRepository();
    static MongoDatabase clientDatabase = repository.getDatabase();
    static ClientRepositoryMongoDB clientRepository = new ClientRepositoryMongoDB(clientDatabase.getCollection("clients_test",Client.class));

    @Test
    void saveClientTest() {
        assertEquals(clientRepository.saveClient(testClient1),testClient1);
    }

    @Test
    void deleteClientTest() {
        clientRepository.saveClient(testClient2);
       assertEquals(clientRepository.deleteClient(testClient2.getEntityId()),true);
    }

    @Test
    void findClientByIdTest() {
        Client client = clientRepository.saveClient(testClient2);
        assertEquals(clientRepository.findClientById(client.getEntityId()),client);

    }
    @AfterAll
    static void cleanDataBase(){
        assertEquals(clientRepository.findAllClients().size(),1);
        clientDatabase.getCollection("clients_test").drop(); // Remove the collection

    }


}
