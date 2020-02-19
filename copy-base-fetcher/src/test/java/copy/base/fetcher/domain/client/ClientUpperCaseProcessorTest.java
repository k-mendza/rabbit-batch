package copy.base.fetcher.domain.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientUpperCaseProcessorTest {
    ClientUpperCaseProcessor clientUpperCaseProcessor;
    Client mockedClient = ClientMock.MOCKED_CLIENT;

    @BeforeEach
    void setUp() {
        clientUpperCaseProcessor = new ClientUpperCaseProcessor();
    }

    @Test
    void process() {
        Client processedClient = clientUpperCaseProcessor.process(mockedClient);
        assert processedClient != null;
        assertEquals(mockedClient.getId(), processedClient.getId());
        assertEquals(mockedClient.getFirstName().toUpperCase(), processedClient.getFirstName());
        assertEquals(mockedClient.getLastName().toUpperCase(), processedClient.getLastName());
        assertEquals(mockedClient.getEmail().toUpperCase(), processedClient.getEmail());
        assertEquals(mockedClient.getPhone(), processedClient.getPhone());
    }
}
