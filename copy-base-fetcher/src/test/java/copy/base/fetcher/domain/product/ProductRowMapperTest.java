package copy.base.fetcher.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {
    ProductRowMapper productRowMapper;
    ResultSet resultSetMock;
    Product mockedProduct = ProductMock.MOCKED_PRODUCT;


    @BeforeEach
    void setUp() throws SQLException {
        productRowMapper = new ProductRowMapper();
        resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getLong(ProductRowMapper.ID_FIELD_NAME)).thenReturn(mockedProduct.getId());
        when(resultSetMock.getString(ProductRowMapper.NAME_FIELD_NAME)).thenReturn(mockedProduct.getName());
        when(resultSetMock.getString(ProductRowMapper.DESCRIPTION_FIELD_NAME)).thenReturn(mockedProduct.getDescription());
        when(resultSetMock.getLong(ProductRowMapper.CATEGORY_ID_FIELD_NAME)).thenReturn(mockedProduct.getCategoryId());
        when(resultSetMock.getBigDecimal(ProductRowMapper.PRICE_FIELD_NAME)).thenReturn(mockedProduct.getPrice());
        when(resultSetMock.getString(ProductRowMapper.CURRENCY_FIELD_NAME)).thenReturn(mockedProduct.getCurrency());
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    void mapRow() throws SQLException {
        Product mappedProduct = productRowMapper.mapRow(resultSetMock, 1);
        assert mappedProduct != null && mappedProduct.equals(mockedProduct);
    }
}
