package acceptance.steps;

import io.cucumber.java.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Hooks {
    @Before
    public void doSomethingBefore() throws SQLException {
        resetDatabase();
    }

    private void resetDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection ("jdbc:h2:/data/kitchensinkDB;;AUTO_SERVER=TRUE", "sa","sa");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from Member where id <> 0");
        conn.close();
    }
}
