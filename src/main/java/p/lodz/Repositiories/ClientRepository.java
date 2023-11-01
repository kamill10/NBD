package p.lodz.Repositiories;

import p.lodz.Model.Client;

import java.util.List;

public interface ClientRepository {
    Client saveClient(Client client);
    Client archiveClient(Long id);
    Client findClientById(Long id);
    List<Client> findAllClients(boolean archived);
}
