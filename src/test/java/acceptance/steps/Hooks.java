package acceptance.steps;

import io.cucumber.java.Before;

import java.sql.SQLException;

public class Hooks {
    private final ScenarioContext scenarioContext;

    @Before
    public void beforeScenario() throws SQLException {
        scenarioContext.getDatabase().resetDatabase();
    }

    public Hooks(ScenarioContext scenarioContext){
        this.scenarioContext = scenarioContext;
    }
}
