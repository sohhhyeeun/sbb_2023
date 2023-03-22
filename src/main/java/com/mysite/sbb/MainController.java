package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    @ResponseBody //응답 그대로 고객에게 보여주겠다.
    public String index() {
        return "안녕하세요 sbb에 오신 것을 환영합니다.";
    }

    //root url
    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
}
