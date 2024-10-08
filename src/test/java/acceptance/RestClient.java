package acceptance;

import KitchenSink.Member;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class RestClient {

    private final ScenarioContext scenarioContext;

    @Value("${rest.url}")
    private String baseUrl;

    public RestClient(ScenarioContext scenarioContext){

        this.scenarioContext = scenarioContext;
    }

    @SneakyThrows
    public void getMemberById(int memberId) {
        String url = baseUrl + "/" + memberId;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        sendRequest(request);
    }

    @SneakyThrows
    public void getAllMembers() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        sendRequest(request);
    }

    @SneakyThrows
    public void createMember(Member member) {
        Gson gson = new Gson();
        String memberAsJson = gson.toJson(member);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(memberAsJson))
                .build();

        sendRequest(request);
    }

    private void sendRequest(HttpRequest request) throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            scenarioContext.setLastStatusCode(response.statusCode());
            scenarioContext.setLastResponseBody(response.body());
        }
    }
}
