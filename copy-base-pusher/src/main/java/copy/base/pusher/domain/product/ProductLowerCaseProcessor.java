package copy.base.pusher.domain.product;

import org.springframework.batch.item.ItemProcessor;

public class ProductLowerCaseProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(Product product) {
        return new Product(product.getId(),
                product.getName().toLowerCase(),
                product.getDescription().toLowerCase(),
                product.getCategoryId(),
                product.getPrice(),
                product.getCurrency().toLowerCase());
    }
}
