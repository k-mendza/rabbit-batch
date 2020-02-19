package copy.base.pusher.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientLowerCaseProcessorTest {
    Client mockedClient = ClientMock.MOCKED_CLIENT;
    ClientLowerCaseProcessor clientLowerCaseProcessor;


    @BeforeEach
    void setUp() {
        clientLowerCaseProcessor = new ClientLowerCaseProcessor();
    }

    @Test
    void process() {
        Client processedClient = clientLowerCaseProcessor.process(mockedClient);
        assert processedClient != null;
        assertEquals(mockedClient.getId(), processedClient.getId());
        assertEquals(mockedClient.getFirstName().toLowerCase(), processedClient.getFirstName());
        assertEquals(mockedClient.getLastName().toLowerCase(), processedClient.getLastName());
        assertEquals(mockedClient.getEmail().toLowerCase(), processedClient.getEmail());
        assertEquals(mockedClient.getPhone(), processedClient.getPhone());
    }
}
