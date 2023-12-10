package p.lodz.Model.Dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import p.lodz.Model.Product;

import java.util.List;
import java.util.UUID;

@Dao
public interface ProductDao {
    @Insert
    public Product create(Product product);

    @Delete(ifExists = true)
    public boolean delete(Product product);

    @Select
    public Product findProduct(UUID id);

    @Select
    public List<Product> findAll();

    @Update
        public void update(Product product);

    @Update(customWhereClause = "id = id AND archived = :archived")
    boolean archiveProduct(Product product , UUID id , boolean archived);

    @Update(customWhereClause = "id = id AND number_of_products = :numberOfProducts")
    boolean decrementProducts(Product product , UUID id , int numberOfProducts);




}
