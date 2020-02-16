package copy.base.fetcher.domain.client;

import org.springframework.batch.item.ItemProcessor;

public class ClientUpperCaseProcessor implements ItemProcessor<Client, Client> {

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
