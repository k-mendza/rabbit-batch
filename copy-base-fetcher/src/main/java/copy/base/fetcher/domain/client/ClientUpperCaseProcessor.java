package copy.base.fetcher.domain.client;

import org.springframework.batch.item.ItemProcessor;

public class ClientUpperCaseProcessor implements ItemProcessor<Client, Client> {

    @Override
    public Client process(Client client) {
        return new Client(client.getId(),
                client.getFirstName().toUpperCase(),
                client.getLastName().toUpperCase(),
                client.getEmail().toUpperCase(),
                client.getPhone());
    }
}
