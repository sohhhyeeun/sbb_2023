package com.mysite.sbb.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question") //공통 접두어는 RequestMapping으로 
@RequiredArgsConstructor
@Controller
public class QuestionController {
    /*
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = questionRepository.findAll();

        //html에서 questionList를 사용하기 위해
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    */
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        //@Valid QuestionForm questionForm: questionForm을 바인딩 할 때 유효성 체크
        if (bindingResult.hasErrors()) {
            return "question_form";    //폼을 보여준다.
        }
        questionService.create(questionForm.getSubject(), questionForm.getContent());

        return "redirect:/question/list";    // 질문 저장후 질문목록으로 이동
    }
}
