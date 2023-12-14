package p.lodz.Repositiories.CassandraImplementations;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;

import p.lodz.Model.Client;
import p.lodz.Model.Dao.ClientDao;
import p.lodz.Model.Mapper.ClientMapper;
import p.lodz.Model.Mapper.ClientMapperBuilder;
import p.lodz.Model.Mapper.ProductMapperBuilder;
import p.lodz.Model.Provider.ClientTypeRepo;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;
import p.lodz.Model.Type.Standard;

import java.util.UUID;

public class ClientRepository implements p.lodz.Repositiories.ClientRepository {

    private static CqlSession session;
    ClientMapper clientMapper;
    ClientDao clientDao;
    ClientTypeRepo clientTypeRepo;


    public ClientRepository(CqlSession session) {
        ClientRepository.session = session;
        prepareTables();
        clientMapper = new ClientMapperBuilder(session).build();
        clientDao = clientMapper.clientDao();
        clientTypeRepo = new ClientTypeRepo(session);
        if(!clientTypeRepo.findByType("premium")){
            clientTypeRepo.create(new Premium());
        }
        if(!clientTypeRepo.findByType("standard")){
            clientTypeRepo.create(new Standard());
        }
        if(!clientTypeRepo.findByType("deluxe")){
            clientTypeRepo.create(new PremiumDeluxe());
        }
    }
    public static void prepareTables(){

        session.execute(SchemaBuilder.createTable(CqlIdentifier.fromCql("clients"))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier.fromCql("id"), DataTypes.UUID)
                .withClusteringColumn(CqlIdentifier.fromCql("last_name"),DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("first_name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("money_spent"), DataTypes.DOUBLE)
                .withColumn(CqlIdentifier.fromCql("client_type"), DataTypes.TEXT)
                .withColumn(CqlIdentifier.fromCql("address_client"), DataTypes.TEXT)
                .build());


    }


    @Override
    public void saveClient(Client client) {
         clientDao.create(client);
    }

    @Override
    public boolean deleteClient(Client client ) {
        return clientDao.delete(client );
    }

    @Override
    public Client findClientById(UUID id) {
        return clientDao.getById(id);
    }

    @Override
    public void  update(Client client) {
         clientDao.update(client);
    }
}
