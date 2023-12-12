package p.lodz.RepositoryTests;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.Standard;
import p.lodz.Repositiories.CassandraConfig;
import p.lodz.Repositiories.CassandraImplementations.ClientRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    static CassandraConfig config = new CassandraConfig();

    static p.lodz.Repositiories.ClientRepository clientRepository = new ClientRepository(config.getSession());

    @Test
    public void testSaveClient() {
        Client client = new Client("TestFirstName", "TestLastName", "premium", new Address("TestCity", "TestStreet", "TestNumber"));

        clientRepository.saveClient(client);

        Client retrievedClient = clientRepository.findClientById(client.getId());
        assertNotNull(retrievedClient);

        assertEquals(client.getFirstName(), retrievedClient.getFirstName());
        assertEquals(client.getLastName(), retrievedClient.getLastName());
    }

    @Test
    public void testUpdateClient() {
        Client client = new Client("TestFirstName", "TestLastName", "premium", new Address("TestCity", "TestStreet", "TestNumber"));

        clientRepository.saveClient(client);

        client.setFirstName("UpdatedFirstName");
        clientRepository.update(client);

        Client updatedClient = clientRepository.findClientById(client.getId());

        assertNotNull(updatedClient);

        assertEquals("UpdatedFirstName", updatedClient.getFirstName());
    }

    @Test
    public void testFindClientById() {
        Client client = new Client("TestFirstName", "TestLastName", "premium", new Address("TestCity", "TestStreet", "TestNumber"));

        clientRepository.saveClient(client);

        Client retrievedClient = clientRepository.findClientById(client.getId());

        assertNotNull(retrievedClient);

        assertEquals(client.getFirstName(), retrievedClient.getFirstName());
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("TestFirstName", "TestLastName", "premium", new Address("TestCity", "TestStreet", "TestNumber"));

        clientRepository.saveClient(client);

        boolean deletionResult = clientRepository.deleteClient(client);

        assertTrue(deletionResult);

        Client deletedClient = clientRepository.findClientById(client.getId());

        assertNull(deletedClient);
    }


}
