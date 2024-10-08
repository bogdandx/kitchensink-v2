package acceptance;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class Database {
    @SneakyThrows
    public void resetDatabase() {
        executeQuery("delete from Member where id <> 0");
    }

    @SneakyThrows
    public void deleteMember(int memberId) {
        executeQuery(String.format("delete from Member where id = %d", memberId));
    }

    private static void executeQuery(String sql) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:/data/kitchensinkDB;;AUTO_SERVER=TRUE", "sa", "sa");
        Statement st = conn.createStatement();
        st.executeUpdate(sql);
        conn.close();
    }
}
