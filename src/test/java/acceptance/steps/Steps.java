package acceptance.steps;

import KitchenSink.Member;
import com.google.gson.Gson;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Steps {

    @Value("${rest.url}")
    private String baseUrl;
    private int statusCode;
    private String responseBody;

    @When("retrieving member with id {long}")
    public void retrieving_member_with_id(long memberId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String url = baseUrl + memberId;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        statusCode = response.statusCode();
        responseBody = response.body();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the following member should be returned:")
    public void the_following_member_should_be_returned(io.cucumber.datatable.DataTable dataTable) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Member fetchedMember = objectMapper.readValue(responseBody, Member.class);

        Member expectedMember = extractMemberFrom(dataTable);

        assertEquals(expectedMember.getId(), fetchedMember.getId());
        assertEquals(expectedMember.getName(), fetchedMember.getName());
        assertEquals(expectedMember.getPhoneNumber(), fetchedMember.getPhoneNumber());
        assertEquals(expectedMember.getEmail(), fetchedMember.getEmail());
    }

    @When("creating the following member")
    public void creating_the_following_member(io.cucumber.datatable.DataTable dataTable) throws IOException, InterruptedException, SQLException {
        cleanDatabase();

        Member member = extractMemberFrom(dataTable);

        Gson gson = new Gson();
        String memberAsJson = gson.toJson(member);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(memberAsJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        statusCode = response.statusCode();
        responseBody = response.body();
    }

    @Then("the member should be returned when retrieved by id with the following attributes")
    public void the_member_should_be_returned_when_retrieved_by_id_with_the_following_attributes(io.cucumber.datatable.DataTable dataTable) throws IOException, InterruptedException {
        Member member = extractMemberFrom(dataTable);

        retrieving_member_with_id(member.getId());

        the_following_member_should_be_returned(dataTable);
    }

    private void cleanDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection ("jdbc:h2:/data/kitchensinkDB;;AUTO_SERVER=TRUE", "sa","sa");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from Member where id <> 0");
        conn.close();
    }


    private static Member extractMemberFrom(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        if (rows.size() != 1) {
            throw new InvalidParameterException("This method supports data tables with exactly one expectedMember");
        }

        Map<String, String> columns = rows.get(0);

        Long id = null;
        if (columns.containsKey("Id")){
            id = Long.parseLong(columns.get("Id"));
        }

        return Member.builder()
                .id(id)
                .name(columns.get("Name"))
                .phoneNumber(columns.get("Phone Number"))
                .email(columns.get("Email"))
                .build();
    }
}
