package copy.base.fetcher.domain.product;

import org.springframework.batch.item.ItemProcessor;

public class ProductUpperCaseProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) {
        return new Product(product.getId(),
                product.getName().toUpperCase(),
                product.getDescription().toUpperCase(),
                product.getCategoryId(),
                product.getPrice(),
                product.getCurrency().toUpperCase());
    }
}
