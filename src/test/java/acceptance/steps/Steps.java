package acceptance.steps;

import KitchenSink.Member;
import acceptance.Database;
import acceptance.RestClient;
import acceptance.ScenarioContext;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCollection;

public class Steps {

    private final ScenarioContext scenarioContext;
    private final RestClient restClient;
    private final Database database;

    public Steps(ScenarioContext scenarioContext, RestClient restClient, Database database){

        this.scenarioContext = scenarioContext;
        this.restClient = restClient;
        this.database = database;
    }

    @Given("member with id {int} does not exist")
    public void member_with_id_does_not_exist(int memberId) {
        database.deleteMember(memberId);
    }

    @When("retrieving member with id {int}")
    public void retrieving_member_with_id(int memberId) {
        restClient.getMemberById(memberId);
    }

    @When("retrieving all members")
    public void retrieving_all_members() {
        restClient.getAllMembers();
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int expectedStatusCode) {
        assertThat(scenarioContext.getLastStatusCode()).isEqualTo(expectedStatusCode);
    }

    @Then("the following member should be returned:")
    public void the_following_member_should_be_returned(DataTable dataTable) {
        Member fetchedMember = extractSingleMemberFromResponseBody(scenarioContext.getLastResponseBody());

        Member expectedMember = extractSingleMemberFromTable(dataTable);

        assertThat(fetchedMember).isEqualTo(expectedMember);
    }

    @Then("the following members should be returned:")
    public void the_following_members_should_be_returned(DataTable dataTable) {
        Member[] fetchedMembers = extractAllMembersFromResponseBody(scenarioContext.getLastResponseBody());

        List<Member> expectedMembers = extractAllMembersFromTable(dataTable);

        assertThat(fetchedMembers.length).isEqualTo(expectedMembers.size());
        assertThatCollection(Arrays.asList(fetchedMembers)).isEqualTo(expectedMembers);
    }

    @Given("the following members exist")
    public void the_following_members_exist(DataTable dataTable) {
        Member member = extractSingleMemberFromTable(dataTable);
        restClient.createMember(member);
    }

    @When("creating the following member")
    public void creating_the_following_member(DataTable dataTable) {
        Member member = extractSingleMemberFromTable(dataTable);
        restClient.createMember(member);
    }

    @Then("the list of persisted members should be equal to")
    public void the_list_of_persisted_members_should_be_equal_to(DataTable dataTable) {
        List<Member> expectedMembers = extractAllMembersFromTable(dataTable);

        restClient.getAllMembers();

        Member[] fetchedMembers = extractAllMembersFromResponseBody(scenarioContext.getLastResponseBody());

        assertThat(fetchedMembers.length).isEqualTo(expectedMembers.size());
        assertThatCollection(Arrays.asList(fetchedMembers))
                .usingElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(expectedMembers);
    }

    private static Member extractSingleMemberFromTable(DataTable dataTable){
        List<Member> members = extractAllMembersFromTable(dataTable);

        if (members.size() != 1){
            throw new InvalidParameterException("Only data tables with exactly one row are supported!");
        }

        return members.getFirst();
    }

    private static List<Member> extractAllMembersFromTable(DataTable dataTable) {
        List<Member> members = new ArrayList<>();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for(Map<String, String> row : rows){
            Long id = null;
            if (row.containsKey("Id")){
                id = Long.parseLong(row.get("Id"));
            }

            members.add( Member.builder()
                    .id(id)
                    .name(row.get("Name"))
                    .phoneNumber(row.get("Phone Number"))
                    .email(row.get("Email"))
                    .build());
        }

        return members;
    }

    @SneakyThrows
    private Member[] extractAllMembersFromResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, Member[].class);
    }

    @SneakyThrows
    private Member extractSingleMemberFromResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, Member.class);
    }
}
