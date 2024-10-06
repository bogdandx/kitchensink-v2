package KitchenSink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}
