package copy.base.fetcher.domain.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientRowMapperTest {
    private static long MOCKED_CLIENT_ID = 1L;
    private static String MOCKED_CLIENT_FIRST_NAME = "John";
    private static String MOCKED_CLIENT_LAST_NAME = "Smith";
    private static String MOCKED_CLIENT_EMAIL = "johnsmith@gmail.com";
    private static String MOCKED_CLIENT_PHONE = "555218475";

    ClientRowMapper clientRowMapper;
    ResultSet resultSetMock;

    @BeforeEach
    void setUp() throws SQLException {
        clientRowMapper = new ClientRowMapper();
        resultSetMock = Mockito.mock(ResultSet.class);
        Mockito.when(resultSetMock.getLong(ClientRowMapper.CLIENT_ID)).thenReturn(MOCKED_CLIENT_ID);
        Mockito.when(resultSetMock.getString(ClientRowMapper.CLIENT_FIRST_NAME)).thenReturn(MOCKED_CLIENT_FIRST_NAME);
        Mockito.when(resultSetMock.getString(ClientRowMapper.CLIENT_LAST_NAME)).thenReturn(MOCKED_CLIENT_LAST_NAME);
        Mockito.when(resultSetMock.getString(ClientRowMapper.CLIENT_EMAIL)).thenReturn(MOCKED_CLIENT_EMAIL);
        Mockito.when(resultSetMock.getString(ClientRowMapper.CLIENT_PHONE)).thenReturn(MOCKED_CLIENT_PHONE);
        Mockito.when(resultSetMock.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    void mapRow() throws SQLException {
        Client mockedClient = clientRowMapper.mapRow(resultSetMock, 1);
        assert mockedClient != null;
        assertEquals(MOCKED_CLIENT_ID, mockedClient.getId());
        assertEquals(MOCKED_CLIENT_FIRST_NAME, mockedClient.getFirstName());
        assertEquals(MOCKED_CLIENT_LAST_NAME, mockedClient.getLastName());
        assertEquals(MOCKED_CLIENT_EMAIL, mockedClient.getEmail());
        assertEquals(MOCKED_CLIENT_PHONE, mockedClient.getPhone());
    }
}
