package copy.base.fetcher.domain.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientRowMapperTest {
    ClientRowMapper clientRowMapper;
    ResultSet resultSetMock;
    Client mockedClient = ClientMock.MOCKED_CLIENT;

    @BeforeEach
    void setUp() throws SQLException {
        clientRowMapper = new ClientRowMapper();
        resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getLong(ClientRowMapper.ID_FIELD_NAME)).thenReturn(mockedClient.getId());
        when(resultSetMock.getString(ClientRowMapper.FIRST_NAME_FIELD_NAME)).thenReturn(mockedClient.getFirstName());
        when(resultSetMock.getString(ClientRowMapper.LAST_NAME_FIELD_NAME)).thenReturn(mockedClient.getLastName());
        when(resultSetMock.getString(ClientRowMapper.EMAIL_FIELD_NAME)).thenReturn(mockedClient.getEmail());
        when(resultSetMock.getString(ClientRowMapper.PHONE_FIELD_NAME)).thenReturn(mockedClient.getPhone());
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    void mapRow() throws SQLException {
        Client mappedClient = clientRowMapper.mapRow(resultSetMock, 1);
        assert mappedClient != null && mappedClient.equals(mockedClient);
    }
}
