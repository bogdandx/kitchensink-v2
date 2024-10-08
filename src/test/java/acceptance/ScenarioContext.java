package acceptance;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class ScenarioContext {
    private int lastStatusCode;
    private String lastResponseBody;

}
