package copy.base.pusher.domain;

import org.springframework.batch.item.ItemProcessor;

public class ClientLowerCaseProcessor implements ItemProcessor<Client, Client> {

    @Override
    public Client process(Client client) {
        return new Client(client.getId(),
                client.getFirstName().toUpperCase(),
                client.getLastName().toUpperCase(),
                client.getEmail().toUpperCase(),
                client.getPhone());
    }
}
