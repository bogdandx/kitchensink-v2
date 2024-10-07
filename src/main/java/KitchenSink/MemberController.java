package KitchenSink;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/{memberId}")
    public Member getMember(@PathVariable int memberId){
        Member member = memberService.getMember(memberId);
        if (member == null){
            throw new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return member;
    }
}
