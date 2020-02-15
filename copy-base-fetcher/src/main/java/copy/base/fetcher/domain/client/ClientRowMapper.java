package copy.base.fetcher.domain.client;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    public static final String CLIENT_ID = "id";
    public static final String CLIENT_FIRST_NAME = "firstName";
    public static final String CLIENT_LAST_NAME = "lastName";
    public static final String CLIENT_EMAIL = "email";
    public static final String CLIENT_PHONE = "phone";

    @Override
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Client(resultSet.getLong(CLIENT_ID),
                resultSet.getString(CLIENT_FIRST_NAME),
                resultSet.getString(CLIENT_LAST_NAME),
                resultSet.getString(CLIENT_EMAIL),
                resultSet.getString(CLIENT_PHONE));
    }
}
