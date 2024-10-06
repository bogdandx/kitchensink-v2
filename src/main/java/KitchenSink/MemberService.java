package KitchenSink;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    public Member getMember(int memberId) {
        return memberRepository.findById(memberId);
    }
}
