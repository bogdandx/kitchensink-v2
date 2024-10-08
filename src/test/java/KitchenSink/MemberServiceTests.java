package KitchenSink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceTests {
    private MemberService memberService;
    private MemberRepository repository;

    @BeforeEach
    public void setup(){
        repository = mock(MemberRepository.class);
        memberService = new MemberService(repository);
    }

    @Test
    public void should_get_member_by_id_from_repository(){
        Member expectedMember = new Member();
        when(repository.findById(65)).thenReturn(expectedMember);

        Member member = memberService.getMember(65);

        assertThat(member).isSameAs(expectedMember);
    }

    @Test
    public void should_get_all_members_from_repository(){
        List<Member> expectedMembers = List.of(new Member());
        when(repository.findAll()).thenReturn(expectedMembers);

        List<Member> allMembers = memberService.getAllMembers();

        assertThatCollection(allMembers).isEqualTo(expectedMembers);
    }

    @Test
    public void should_create_member_using_repository() throws EmailTakenException {
        Member member = new Member();

        memberService.createMember(member);

        verify(repository).save(member);
    }

    @Test
    public void should_throw_email_taken_exception_if_email_already_in_use() {
        when(repository.existsMemberByEmail("tara.scott@gmail.com")).thenReturn(true);

        Member member = Member.builder()
                .email("tara.scott@gmail.com").build();

        Throwable throwable = catchThrowable(() -> memberService.createMember(member));

        assertThat(throwable).isInstanceOf(EmailTakenException.class);
    }
}
