package p.lodz.Repositiories.Implementations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import p.lodz.Model.Client;
import p.lodz.Repositiories.ClientRepository;

import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {
    private final EntityManager em;
    public ClientRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Client saveClient(Client client) {
        em.getTransaction().begin();
        if(client.getId() == null) em.persist(client);
        else client = em.merge(client);
        em.getTransaction().commit();
        return client;
    }

    @Override
    public Client archiveClient(Long id) {
        em.getTransaction().begin();
        Client client = em.find(Client.class, id);
        if (client != null) {
            client.setArchived(true);
            em.merge(client);
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
        }
        return client;
    }

    @Override
    public Client findClientById(Long id) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> findAllClients(boolean archived) {
        Query query = em.createQuery("SELECT c FROM Client c WHERE c.archived = :ar");
        query.setParameter("ar", archived);
        return query.getResultList();
    }
}
