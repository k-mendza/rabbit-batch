package copy.base.pusher.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ClientLowerCaseProcessor implements ItemProcessor<Client, Client> {

    private static final Logger log = LoggerFactory.getLogger(ClientLowerCaseProcessor.class);

    @Override
    public Client process(Client client) {
        Long id = client.getId();
        String firstName = client.getFirstName().toUpperCase();
        String lastName = client.getLastName().toUpperCase();
        String email = client.getEmail().toUpperCase();
        String phone = client.getPhone();
        return new Client(id, firstName, lastName, email, phone);
    }
}
