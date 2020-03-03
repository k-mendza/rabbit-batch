package copy.base.fetcher.domain.client;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Setter
@Getter
@ToString
@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Client {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
