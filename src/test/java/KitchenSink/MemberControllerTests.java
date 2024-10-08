package KitchenSink;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

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
    public void get_all_member_should_return_members_from_service_as_json_array() throws Exception {
        when(memberService.getAllMembers()).thenReturn(
                Arrays.asList(
                    Member.builder()
                            .id(54L)
                            .name("Bob")
                            .phoneNumber("9876543210")
                            .email("bob@gmail.com").build(),
                    Member.builder()
                            .id(67L)
                            .name("Rick")
                            .phoneNumber("4543534534")
                            .email("rick@gmail.com").build()));

        this.mockMvc.perform(get("/members"))
                .andExpect(content()
                        .json("""
                        [{
                            "id":54,
                            "name":"Bob",
                            "phoneNumber":"9876543210",
                            "email": "bob@gmail.com"
                        },
                        {
                            "id":67,
                            "name":"Rick",
                            "phoneNumber":"4543534534",
                            "email": "rick@gmail.com"
                        }]"""));
    }

    @Test
    public void create_member_should_invoke_service_with_member_data() throws Exception, EmailTakenException {
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
        assertThat(deserializedMember).isEqualTo(Member.builder()
                                                    .id(null)
                                                    .name("Rick")
                                                    .phoneNumber("73738383990")
                                                    .email("rick@gmail.com")
                                                .build());
    }

    @Test
    public void create_member_should_return_status_409_if_email_in_use() throws Exception, EmailTakenException {
        doThrow(new EmailTakenException()).when(memberService).createMember(any());

        this.mockMvc.perform(post("/members").content("""
                        {
                            "id":null,
                            "name":"Rick",
                            "phoneNumber":"73738383990",
                            "email": "rick@gmail.com"
                        }""").header("Content-Type", "application/json"))
                .andExpect(status().isConflict())
                .andExpect(content().json("""
                        {
                            "email":"Email taken"
                        }"""));
    }

    @Test
    @Disabled
    public void create_member_should_return_status_400_if_phone_is_too_long() throws Exception {
        this.mockMvc.perform(post("/members").content("""
                        {
                            "id":null,
                            "name":"Rick",
                            "phoneNumber":"7334373838390",
                            "email": "rick@gmail.com"
                        }""").header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "phoneNumber":"size must be between 10 and 12"
                        }"""));
    }

    @Test
    public void create_member_should_return_status_400_if_phone_contains_non_numeric_characters() throws Exception {
        this.mockMvc.perform(post("/members").content("""
                        {
                            "id":null,
                            "name":"Rick",
                            "phoneNumber":"123-456-7890",
                            "email": "rick@gmail.com"
                        }""").header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "phoneNumber":"numeric value out of bounds (<12 digits>.<0 digits> expected)"
                        }"""));
    }

    @Test
    public void create_member_should_return_status_400_if_email_not_well_formed() throws Exception {
        this.mockMvc.perform(post("/members").content("""
                        {
                            "id":null,
                            "name":"Rick",
                            "phoneNumber":"1234567890",
                            "email": "rick-at-gmail.com"
                        }""").header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                            "email":"must be a well-formed email address"
                        }"""));
    }
}
