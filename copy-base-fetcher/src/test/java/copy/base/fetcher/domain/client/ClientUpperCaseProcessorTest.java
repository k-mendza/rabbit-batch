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
        Client clientAfterProcessing = clientUpperCaseProcessor.process(mockedClient);
        assert clientAfterProcessing != null;
        assertEquals(mockedClient.getId(), clientAfterProcessing.getId());
        assertEquals(mockedClient.getFirstName().toUpperCase(), clientAfterProcessing.getFirstName());
        assertEquals(mockedClient.getLastName().toUpperCase(), clientAfterProcessing.getLastName());
        assertEquals(mockedClient.getEmail().toUpperCase(), clientAfterProcessing.getEmail());
        assertEquals(mockedClient.getPhone(), clientAfterProcessing.getPhone());
    }
}
