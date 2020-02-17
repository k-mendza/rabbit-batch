package copy.base.fetcher.domain.client;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRowMapper implements RowMapper<Client> {
    public static final String ID_FIELD_NAME = "id";
    public static final String FIRST_NAME_FIELD_NAME = "firstName";
    public static final String LAST_NAME_FIELD_NAME = "lastName";
    public static final String EMAIL_FIELD_NAME = "email";
    public static final String PHONE_FIELD_NAME = "phone";

    @Override
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Client(resultSet.getLong(ID_FIELD_NAME),
                resultSet.getString(FIRST_NAME_FIELD_NAME),
                resultSet.getString(LAST_NAME_FIELD_NAME),
                resultSet.getString(EMAIL_FIELD_NAME),
                resultSet.getString(PHONE_FIELD_NAME));
    }
}
