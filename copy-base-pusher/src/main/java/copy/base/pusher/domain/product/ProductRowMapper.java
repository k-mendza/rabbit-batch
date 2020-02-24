package copy.base.pusher.domain.product;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    public static final String ID_FIELD_NAME = "id";
    public static final String NAME_FIELD_NAME = "name";
    public static final String DESCRIPTION_FIELD_NAME = "description";
    public static final String CATEGORY_ID_FIELD_NAME = "categoryId";
    public static final String PRICE_FIELD_NAME = "price";
    public static final String CURRENCY_FIELD_NAME = "currency";

    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Product(resultSet.getLong(ID_FIELD_NAME),
                resultSet.getString(NAME_FIELD_NAME),
                resultSet.getString(DESCRIPTION_FIELD_NAME),
                resultSet.getLong(CATEGORY_ID_FIELD_NAME),
                resultSet.getBigDecimal(PRICE_FIELD_NAME),
                resultSet.getString(CURRENCY_FIELD_NAME));
    }
}
