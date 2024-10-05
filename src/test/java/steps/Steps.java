package steps;

import KitchenSink.Member;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Steps {

    private final String legacyBaseUrl = "http://localhost:8080/kitchensink/rest/members/";
    private final String baseUrl = "http://localhost:8082/members/";

    private Member fetchedMember;
    private int statusCode;

    @When("retrieving member with id {int} using legacy")
    public void retrieving_member_with_id_using_legacy(int memberId) throws IOException, InterruptedException {
        get_member(legacyBaseUrl, memberId);
    }

    @When("retrieving member with id {int}")
    public void retrieving_member_with_id(int memberId) throws IOException, InterruptedException {
        get_member(baseUrl, memberId);
    }

    private void get_member(String baseUrl, int memberId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String url = baseUrl + memberId;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        statusCode = response.statusCode();
        fetchedMember = objectMapper.readValue(response.body(), Member.class);
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the following member should be returned:")
    public void the_following_member_should_be_returned(io.cucumber.datatable.DataTable dataTable) {
        Member expectedMember = extractMemberFrom(dataTable);

        assertEquals(expectedMember.getId(), fetchedMember.getId());
        assertEquals(expectedMember.getName(), fetchedMember.getName());
        assertEquals(expectedMember.getPhoneNumber(), fetchedMember.getPhoneNumber());
        assertEquals(expectedMember.getEmail(), fetchedMember.getEmail());
    }

    private static Member extractMemberFrom(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        if (rows.size() != 1){
            throw new InvalidParameterException("This method supports data tables with exactly one expectedMember");
        }

        Map<String, String> columns = rows.get(0);
        var member = new Member();
        member.setId(Integer.parseInt(columns.get("Id")));
        member.setName(columns.get("Name"));
        member.setPhoneNumber((columns.get("Phone Number")));
        member.setEmail((columns.get("Email")));

        return member;
    }

}
