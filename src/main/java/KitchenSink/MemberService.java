package KitchenSink;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    public Member getMember(int memberId) {

        return memberRepository.findById(memberId);
    }

    public void createMember(Member member) {
        memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
