package copy.base.fetcher.domain.product;

import org.springframework.batch.item.ItemProcessor;

public class ProductUpperCaseProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) throws Exception {
        Long id = product.getId();
        String name = product.getName().toUpperCase();
        String description = product.getDescription().toUpperCase();
        Long categoryId = product.getCategoryId();
        Double price = product.getPrice();
        String currency = product.getCurrency().toUpperCase();
        return new Product(id, name, description, categoryId, price, currency);
    }
}
