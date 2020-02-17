package copy.base.fetcher.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductUpperCaseProcessorTest {
    ProductUpperCaseProcessor productUpperCaseProcessor;
    Product mockedProduct = ProductMock.MOCKED_PRODUCT;

    @BeforeEach
    void setUp() {
        productUpperCaseProcessor = new ProductUpperCaseProcessor();
    }

    @Test
    void process() {
        Product processedProduct = productUpperCaseProcessor.process(mockedProduct);
        assert processedProduct != null;
        assertEquals(mockedProduct.getId(), processedProduct.getId());
        assertEquals(mockedProduct.getName().toUpperCase(), processedProduct.getName());
        assertEquals(mockedProduct.getDescription().toUpperCase(), processedProduct.getDescription());
        assertEquals(mockedProduct.getCategoryId(), processedProduct.getCategoryId());
        assertEquals(mockedProduct.getPrice(), processedProduct.getPrice());
        assertEquals(mockedProduct.getCurrency(), processedProduct.getCurrency());
    }
}
