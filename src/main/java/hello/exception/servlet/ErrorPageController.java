package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//오류 발생 시 처리할 수 있는 컨트롤러
@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    //예외 객체를 나타낸다.
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    //예외 유형을 나타낸다.
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    //예외 오류 메시지를 나타낸다.
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    //오류가 발생한 URI를 나타낸다.
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    //오류가 발생한 서블릿의 이름을 나타낸다.
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    //HTTP 상태 코드를 나타낸다.
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    //produces -> 클라이언트가 보내는 Accept에 따라 어떤 것이 호출되는 지 알려준다.
    //같은 주소가 있더라도 produces로 JSON을 설정해둔 이 메서드는 application_json으로 요청이 들어오면 실행된다.
    public ResponseEntity<Map<String, Object>> errorPage500Api(
            HttpServletRequest request, HttpServletResponse response) {
        log.info("API errorPage 500");

        Map<String, Object> result = new HashMap<>();
        //여기서 왜 파라미터에 ERROR_EXCEPTION을 넣는거지?
        // -> 요청이 발생했을 때 요청에 에러에 대한 유형이 담겨져 있을 것이기에 Exception 타입으로 오류 정보를 담는 것?
        Exception ex =(Exception) request.getAttribute(ERROR_EXCEPTION);
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        //HttpStatus.valueOf(statusCode) -> statusCode(404와 같은 오류 코드)와 같은 HTTP 상태 코드에 대한 HttpStatus 상수를 얻는다.
        //statusCode = 404, 위 메서드의 return : HttpStatus.NOT_FOUND (404 -> HttpStatus.NOT_FOUND로 리턴해주는 것임.)
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));

    }



    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatcherType={}", request.getDispatcherType());
    }
}
