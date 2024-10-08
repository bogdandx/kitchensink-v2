package KitchenSink;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String,String>> createMember(@Valid @RequestBody Member member) {
        try {
            memberService.createMember(member);
        } catch (EmailTakenException e) {
            Map<String, String> responseObj = new HashMap<>();;
            responseObj.put("email", "Email taken");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObj);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
