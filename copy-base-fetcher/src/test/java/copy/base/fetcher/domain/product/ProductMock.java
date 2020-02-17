package copy.base.fetcher.domain.product;

import java.math.BigDecimal;

public class ProductMock {
    private static final Long MOCKED_PRODUCT_ID = 1L;
    private static final String MOCKED_PRODUCT_NAME = "name";
    private static final String MOCKED_PRODUCT_DESCRIPTION = "description";
    private static final Long MOCKED_PRODUCT_CATEGORY_ID = 0L;
    private static final BigDecimal MOCKED_PRODUCT_PRICE = new BigDecimal("10.99");
    private static final String MOCKED_PRODUCT_CURRENCY = "USD";


    public static final Product MOCKED_PRODUCT = new Product(MOCKED_PRODUCT_ID,
                                                     MOCKED_PRODUCT_NAME,
                                                     MOCKED_PRODUCT_DESCRIPTION,
                                                     MOCKED_PRODUCT_CATEGORY_ID,
                                                     MOCKED_PRODUCT_PRICE,
                                                     MOCKED_PRODUCT_CURRENCY);
}
