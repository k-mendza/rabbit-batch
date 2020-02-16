package copy.base.fetcher.domain.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

class ClientRowMapperTest {
    ClientRowMapper clientRowMapper;
    ResultSet resultSetMock;
    Client mockedClient = ClientMock.MOCKED_CLIENT;

    @BeforeEach
    void setUp() throws SQLException {
        clientRowMapper = new ClientRowMapper();
        resultSetMock = Mockito.mock(ResultSet.class);
        when(resultSetMock.getLong(ClientRowMapper.CLIENT_ID)).thenReturn(mockedClient.getId());
        when(resultSetMock.getString(ClientRowMapper.CLIENT_FIRST_NAME)).thenReturn(mockedClient.getFirstName());
        when(resultSetMock.getString(ClientRowMapper.CLIENT_LAST_NAME)).thenReturn(mockedClient.getLastName());
        when(resultSetMock.getString(ClientRowMapper.CLIENT_EMAIL)).thenReturn(mockedClient.getEmail());
        when(resultSetMock.getString(ClientRowMapper.CLIENT_PHONE)).thenReturn(mockedClient.getPhone());
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    void mapRow() throws SQLException {
        Client mappedClient = clientRowMapper.mapRow(resultSetMock, 1);
        assert mappedClient != null;
        assert mappedClient.equals(mockedClient);
    }
}
