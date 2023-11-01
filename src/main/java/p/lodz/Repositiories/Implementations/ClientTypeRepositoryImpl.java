package p.lodz.Repositiories.Implementations;

import jakarta.persistence.EntityManager;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.ClientTypeRepository;

public class ClientTypeRepositoryImpl implements ClientTypeRepository {
    private final EntityManager em;

    public ClientTypeRepositoryImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public ClientType saveClientType(ClientType clientType) {
//        em.getTransaction().begin();
        if(clientType.getId() == null) em.persist(clientType);
        else clientType = em.merge(clientType);
//        em.getTransaction().commit();
        return clientType;
    }
}
