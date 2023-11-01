package p.lodz.Managers;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import p.lodz.Exceptions.InvalidValueException;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;
import p.lodz.Repositiories.ClientRepository;
import p.lodz.Repositiories.ClientTypeRepository;
import p.lodz.Repositiories.Implementations.ClientRepositoryImpl;
import p.lodz.Repositiories.Implementations.ClientTypeRepositoryImpl;

import java.util.List;
import java.util.Set;

public class ClientManager {
    private final ClientRepository clientRepository;
    private final ClientTypeRepository clientTypeRepository;
    private final Validator validator;

    public ClientManager(EntityManager em, Validator validator) {
        this.clientRepository = new ClientRepositoryImpl(em);
        this.clientTypeRepository = new ClientTypeRepositoryImpl(em);
        this.validator = validator;
    }

    public Client getClient(Long id){
        return clientRepository.findClientById(id);
    }

    public Client registerClient(String firstName, String lastName, String city, String street, String number, ClientType clientType) {
        Address address = new Address(city, street, number);
        Client client = new Client(firstName, lastName, address, clientTypeRepository.saveClientType(clientType));
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<Client> violation : violations) {
                throw new InvalidValueException(violation.getMessage());
            }
        }
        return clientRepository.saveClient(client);
    }

    public List<Client> getAllClients(boolean archived) {
        return clientRepository.findAllClients(archived);
    }

    public Client unregister(Long id){
        return clientRepository.archiveClient(id);
    }
}
