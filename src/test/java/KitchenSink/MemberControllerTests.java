package KitchenSink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTests {
    private MockMvc mockMvc;
    private MemberService memberService;

    @BeforeEach
    public void setup() {
        memberService = mock(MemberService.class);

        this.mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService))
                .build();
    }

    @Test
    public void get_member_should_invoke_service_with_member_id_from_path() throws Exception {
        this.mockMvc.perform(get("/members/4"));

        verify(memberService).getMember(4);
    }

    @Test
    public void get_member_should_return_member_from_service_as_json() throws Exception {
        when(memberService.getMember(4)).thenReturn(
                Member.builder()
                    .id(54L)
                    .name("Bob")
                    .phoneNumber("9876543210")
                    .email("bob@gmail.com").build());

        this.mockMvc.perform(get("/members/4"))
                .andExpect(content()
                        .json("""
                        {
                            "id":54,
                            "name":"Bob",
                            "phoneNumber":"9876543210",
                            "email": "bob@gmail.com"
                        }"""));
    }

    @Test
    public void get_member_should_return_status_200_if_the_service_returned_a_member() throws Exception {
        when(memberService.getMember(4)).thenReturn(new Member());

        this.mockMvc.perform(get("/members/4"))
                .andExpect(status().isOk());
    }

    @Test
    public void get_member_return_status_404_if_the_service_returned_null() throws Exception {
        when(memberService.getMember(4)).thenReturn(null);

        this.mockMvc.perform(get("/members/54"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void create_member_should_invoke_service_with_member_data() throws Exception {
        ArgumentCaptor<Member> argument = ArgumentCaptor.forClass(Member.class);

        this.mockMvc.perform(post("/members").content("""
                        {
                            "id":null,
                            "name":"Rick",
                            "phoneNumber":"73738383990",
                            "email": "rick@gmail.com"
                        }""").header("Content-Type", "application/json"))
                .andExpect(status().isOk());

        verify(memberService).createMember(argument.capture());

        Member deserializedMember = argument.getValue();
        assertMemberEquality(deserializedMember, Member.builder()
                                                    .id(null)
                                                    .name("Rick")
                                                    .phoneNumber("73738383990")
                                                    .email("rick@gmail.com")
                                                .build());
    }

    private void assertMemberEquality(Member actual, Member expected){
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }
}
