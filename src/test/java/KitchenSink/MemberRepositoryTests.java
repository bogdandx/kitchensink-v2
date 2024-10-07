package KitchenSink;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void should_fetch_default_member_from_database(){
        Member member = memberRepository.findById(0);

        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo("John Smith");
        assertThat(member.getPhoneNumber()).isEqualTo("2125551212");
        assertThat(member.getEmail()).isEqualTo("john.smith@mailinator.com");
    }
}
