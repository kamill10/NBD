package p.lodz.Model.Dao;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.mapper.annotations.*;
import jnr.ffi.annotations.In;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;

import java.util.UUID;

@Dao
public interface ClientDao {


    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
    void create(Client client);
    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Client client);
    @StatementAttributes(consistencyLevel = "ONE")
    @Delete
    boolean delete (Client client);
    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
    Client getById(UUID id);

}
