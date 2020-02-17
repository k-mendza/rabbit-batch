package copy.base.fetcher.domain.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
@EqualsAndHashCode
public class Product implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private BigDecimal price;
    private String currency;
}
