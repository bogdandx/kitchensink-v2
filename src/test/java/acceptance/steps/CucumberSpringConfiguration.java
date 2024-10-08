package acceptance.steps;

import acceptance.Database;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = {ScenarioContext.class, Database.class})
public class CucumberSpringConfiguration { }
