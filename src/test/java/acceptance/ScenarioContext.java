package acceptance;

import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {
    private int lastStatusCode;
    private String lastResponseBody;

    public void setLastStatusCode(int lastStatusCode) {
        this.lastStatusCode = lastStatusCode;
    }

    public int getLastStatusCode() {
        return lastStatusCode;
    }

    public void setLastResponseBody(String lastResponseBody) {
        this.lastResponseBody = lastResponseBody;
    }

    public String getLastResponseBody() {
        return lastResponseBody;
    }
}
