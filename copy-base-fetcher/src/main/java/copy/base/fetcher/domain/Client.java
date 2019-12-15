package copy.base.fetcher.domain;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
