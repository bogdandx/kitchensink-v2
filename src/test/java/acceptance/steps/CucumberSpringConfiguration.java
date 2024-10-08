package acceptance.steps;

import acceptance.Database;
import acceptance.RestClient;
import acceptance.ScenarioContext;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = {ScenarioContext.class, Database.class, RestClient.class})
public class CucumberSpringConfiguration { }
