package acceptance;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class Database {
    public void resetDatabase() throws SQLException {
        executeQuery("delete from Member where id <> 0");
    }

    public void deleteMember(int memberId) throws SQLException {
        executeQuery(String.format("delete from Member where id = %d", memberId));
    }

    private static void executeQuery(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:/data/kitchensinkDB;;AUTO_SERVER=TRUE", "sa", "sa");
        Statement st = conn.createStatement();
        st.executeUpdate(sql);
        conn.close();
    }
}
