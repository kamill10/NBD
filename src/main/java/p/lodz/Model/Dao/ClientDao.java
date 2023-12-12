package p.lodz.Model.Dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import jnr.ffi.annotations.In;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;

import java.util.UUID;

@Dao
public interface ClientDao {

    @Insert
    void create(Client client);
    @Update
    void update(Client client);
    @Delete
    boolean delete (Client client);
    @Select
    Client getById(UUID id);

}
