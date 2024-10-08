package KitchenSink;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @GetMapping(value = "/members")
    public Member[] getAllMembers(){
        List<Member> members = memberService.getAllMembers();
        return members.toArray(Member[]::new);
    }

    @PostMapping(value = "/members")
    public void createMember(@RequestBody Member member){
        memberService.createMember(member);
    }
}
