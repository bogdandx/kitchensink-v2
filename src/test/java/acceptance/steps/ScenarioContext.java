package acceptance.steps;

import acceptance.Database;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {
    private final Database database;

    public ScenarioContext(Database database){

        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }
}
