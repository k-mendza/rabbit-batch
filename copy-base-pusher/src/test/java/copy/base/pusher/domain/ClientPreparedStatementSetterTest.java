package copy.base.pusher.domain;

import copy.base.pusher.domain.client.Client;
import copy.base.pusher.domain.client.ClientPreparedStatementSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class ClientPreparedStatementSetterTest {
    PreparedStatement preparedStatementMock;
    Client mockedClient = ClientMock.MOCKED_CLIENT;
    ClientPreparedStatementSetter clientPreparedStatementSetter;

    @BeforeEach
    void setUp() {
        clientPreparedStatementSetter = new ClientPreparedStatementSetter();
    }

    @Test
    void setValues() throws SQLException {
        clientPreparedStatementSetter.setValues(mockedClient, preparedStatementMock);
    }
}
