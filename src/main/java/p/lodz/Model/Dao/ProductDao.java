package p.lodz.Model.Dao;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.mapper.annotations.*;
import p.lodz.Model.Product;

import java.util.List;
import java.util.UUID;

@Dao
public interface ProductDao {

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Insert
     void create(Product product);
    @StatementAttributes(consistencyLevel = "ONE")
    @Delete()
     boolean delete(Product product);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Select
     Product findProduct(UUID id);

    @StatementAttributes(consistencyLevel = "QUORUM")
    @Update
    void update(Product product);


}
