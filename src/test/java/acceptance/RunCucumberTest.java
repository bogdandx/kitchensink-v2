package acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@EnabledOnOs({ OS.WINDOWS })
@CucumberOptions(features = "src/test/resources", glue = "acceptance/steps")
public class RunCucumberTest {
}
