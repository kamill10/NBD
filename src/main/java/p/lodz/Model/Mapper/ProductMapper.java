package p.lodz.Model.Mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import p.lodz.Model.Dao.ProductDao;

@Mapper
public interface ProductMapper {
    @DaoFactory
    ProductDao productDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    ProductDao productDao();
}
