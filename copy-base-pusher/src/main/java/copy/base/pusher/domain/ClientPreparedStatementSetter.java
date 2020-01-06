package copy.base.pusher.domain;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientPreparedStatementSetter implements ItemPreparedStatementSetter<Client> {

    @Override
    public void setValues(Client item, PreparedStatement ps) throws SQLException {
        ps.setLong(1, item.getId());
        ps.setString(2, item.getFirstName());
        ps.setString(3, item.getLastName());
        ps.setString(4, item.getEmail());
        ps.setString(5, item.getPhone());
    }
}
