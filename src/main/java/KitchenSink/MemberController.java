package KitchenSink;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @GetMapping("/members/{memberId}")
    public Member getUniverseMeaning(@PathVariable int memberId){
        Member member = new Member();
        member.setId(memberId);
        return member;
    }
}
