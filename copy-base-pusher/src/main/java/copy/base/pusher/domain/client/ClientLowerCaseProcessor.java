package copy.base.pusher.domain.client;

import org.springframework.batch.item.ItemProcessor;

public class ClientLowerCaseProcessor implements ItemProcessor<Client, Client> {

    @Override
    public Client process(Client client) {
        return new Client(client.getId(),
                client.getFirstName().toLowerCase(),
                client.getLastName().toLowerCase(),
                client.getEmail().toLowerCase(),
                client.getPhone());
    }
}
