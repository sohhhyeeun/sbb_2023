package com.mysite.sbb.answer;

import ch.qos.logback.core.model.Model;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        Question question = questionService.getQuestion(id);
        answerService.create(question, content);

        //처리 요청에 대해서는 처리 후 빨리 떠나는 편이 낫다.
        //단, 적절한 if문을 사용하면 유용할 수도 있다. (머무는 편이 나을 수도 있음.)
        return String.format("redirect:/question/detail/%s", id);
    }
}
