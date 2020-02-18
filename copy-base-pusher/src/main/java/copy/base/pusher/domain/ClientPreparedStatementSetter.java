package copy.base.pusher.domain;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientPreparedStatementSetter implements ItemPreparedStatementSetter<Client> {
    public static final int ID_INDEX = 1;
    public static final int FIRST_NAME_INDEX = 2;
    public static final int LAST_NAME_INDEX = 3;
    public static final int EMAIL_INDEX = 4;
    public static final int PHONE_INDEX = 5;

    @Override
    public void setValues(Client client, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(ID_INDEX, client.getId());
        preparedStatement.setString(FIRST_NAME_INDEX, client.getFirstName());
        preparedStatement.setString(LAST_NAME_INDEX, client.getLastName());
        preparedStatement.setString(EMAIL_INDEX, client.getEmail());
        preparedStatement.setString(PHONE_INDEX, client.getPhone());
    }
}
