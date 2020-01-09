package copy.base.fetcher.domain.product;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Product(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getLong("categoryId"),
                resultSet.getDouble("price"),
                resultSet.getString("currency"));
    }
}
