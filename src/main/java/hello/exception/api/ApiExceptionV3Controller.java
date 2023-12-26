package hello.exception.api;

import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionV3Controller {

    @GetMapping("/api3/members/{id}")
    public ApiExceptionController.MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            // /api/members/ex로 요청이 들어오면 message를 저장.
            throw new RuntimeException("잘못된 사용자"); //RuntimeException("message")
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            //why -> 500Error?
            throw new UserException("사용자 오류");
        }
        return new ApiExceptionController.MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
