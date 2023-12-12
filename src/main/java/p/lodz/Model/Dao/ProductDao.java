package p.lodz.Model.Dao;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.mapper.annotations.*;
import p.lodz.Model.Product;

import java.util.List;
import java.util.UUID;

@Dao
public interface ProductDao {

    @Insert
     void create(Product product);

    @Delete(ifExists = true)
     boolean delete(Product product);

    @Select
     Product findProduct(UUID id);


    @Update
    void update(Product product);

    /*@Update(customWhereClause = "id = id AND archived = :archived")
    boolean archiveProduct(Product product , UUID id , boolean archived);

    @Update(customWhereClause = "id = id AND number_of_products = :numberOfProducts")
    boolean decrementProducts(Product product , UUID id , int numberOfProducts); */




}
