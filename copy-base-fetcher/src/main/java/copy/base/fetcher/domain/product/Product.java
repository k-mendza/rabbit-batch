package copy.base.fetcher.domain.product;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

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
    private Double price;
    private String currency;
}
