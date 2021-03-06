package copy.base.pusher.domain.client;

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
public class Client implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
