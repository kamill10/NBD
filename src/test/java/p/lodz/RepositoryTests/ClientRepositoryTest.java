package p.lodz.RepositoryTests;

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
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.ClientTypeRepository;
import p.lodz.Repositiories.Implementations.ClientRepositoryImpl;
import p.lodz.Repositiories.Implementations.ClientTypeRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ClientRepository clientRepository;
    private static ClientTypeRepository clientTypeRepository;

    @BeforeAll
    static void initTest() {
        emf = Persistence.createEntityManagerFactory("test");
        em = emf.createEntityManager();
        clientRepository = new ClientRepositoryImpl(em);
        clientTypeRepository = new ClientTypeRepositoryImpl(em);
//        em.getTransaction().begin();
    }

    @Test
    void saveClientTest() {
        ClientType clientType = new Premium();
        Address address = new Address("aaa", "bbb", "ccc");
        Client client = new Client("Adam1", "Fajny", address, clientTypeRepository.saveClientType(clientType));
        assertEquals(client, clientRepository.saveClient(client));
    }

    @Test
    void archiveClientTest() {
        ClientType clientType = new Premium();
        Address address = new Address("aaa", "bbb", "ccc");
        Client client = new Client("Adam2", "Fajny", address, clientTypeRepository.saveClientType(clientType));
        Client savedClient = clientRepository.saveClient(client);
        Client client1 = clientRepository.archiveClient(savedClient.getId());
        assertTrue(client1.isArchived());
    }

    @Test
    void findClientByIdTest() {
        ClientType clientType = new Premium();
        Address address = new Address("aaa", "bbb", "ccc");
        Client client = new Client("Adam3", "Fajny", address, clientTypeRepository.saveClientType(clientType));
        Client savedClient = clientRepository.saveClient(client);
        assertEquals(client, clientRepository.findClientById(savedClient.getId()));
    }

    @Test
    void findAllClientsTest() {
        ClientType clientType = new Premium();
        Address address = new Address("aaa", "bbb", "ccc");
        Client client = new Client("Adam4", "Fajny", address, clientTypeRepository.saveClientType(clientType));
        clientRepository.saveClient(client);
        assertEquals(3, clientRepository.findAllClients(false).size());
    }

    @AfterAll
    static void endTest() {
//        em.getTransaction().commit();
        if(emf != null) emf.close();
    }
}
