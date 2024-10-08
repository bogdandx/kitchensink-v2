package acceptance.steps;

import acceptance.Database;
import io.cucumber.java.Before;

public class Hooks {
    private final Database database;

    @Before
    public void beforeScenario() {
        database.resetDatabase();
    }

    public Hooks(Database database){
        this.database = database;
    }
}
