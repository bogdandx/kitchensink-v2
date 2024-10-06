package KitchenSink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void should_invoke_service_with_member_id_from_path() throws Exception {
        this.mockMvc.perform(get("/members/4"));

        verify(memberService).getMember(4);
    }

    @Test
    public void should_return_member_from_service_as_json() throws Exception {
        Member expectedMember = new Member();
        expectedMember.setId(54);
        expectedMember.setName("Bob");
        expectedMember.setPhoneNumber("9876543210");
        expectedMember.setEmail("bob@gmail.com");

        when(memberService.getMember(4)).thenReturn(expectedMember);

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
    public void should_return_status_200_if_the_service_returned_a_member() throws Exception {
        when(memberService.getMember(4)).thenReturn(new Member());

        this.mockMvc.perform(get("/members/54"))
                .andExpect(status().isOk());
    }
}
