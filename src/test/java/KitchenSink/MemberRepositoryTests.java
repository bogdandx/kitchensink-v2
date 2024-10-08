package KitchenSink;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @EnabledOnOs({ OS.WINDOWS })
    public void should_fetch_default_member_from_database(){
        Member member = memberRepository.findById(0);

        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("John Smith");
        assertThat(member.getPhoneNumber()).isEqualTo("2125551212");
        assertThat(member.getEmail()).isEqualTo("john.smith@mailinator.com");
    }

    @Test
    @EnabledOnOs({ OS.WINDOWS })
    public void should_fetch_all_members_from_database(){
        List<Member> members = memberRepository.findAll();

        assertThat(members).isNotNull();
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.getFirst().getName()).isEqualTo("John Smith");
        assertThat(members.getFirst().getPhoneNumber()).isEqualTo("2125551212");
        assertThat(members.getFirst().getEmail()).isEqualTo("john.smith@mailinator.com");
    }

    @Test
    public void should_save_member_to_database(){
        Member member = Member.builder()
                        .name("Rick")
                        .email("rick@gmail.com")
                        .phoneNumber("5436788665")
                .build();

        memberRepository.save(member);

        List<Member> allMembers = memberRepository.findAll();
        assertThat(allMembers).contains(member);
    }
}
