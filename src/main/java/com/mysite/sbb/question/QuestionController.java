package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue="0") int page) {
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        //get은 데이터가 없는 상태에서 실행되기 때문에 @Valid 사용 X
        //QuestionForm questionForm 써주는 이유, questionForm.html에서 questionForm 변수가 없으면 실행이 되지 않기 때문
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        //@Valid QuestionForm questionForm: questionForm을 바인딩 할 때 유효성 체크
        //(암기)Model model 정의하지 않아도 바로 html 접근 가능
        if (bindingResult.hasErrors()) {
            return "question_form";    //폼을 보여준다.
        }

        SiteUser siteUser = userService.getUser(principal.getName());

        questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

        return "redirect:/question/list";    // 질문 저장후 질문목록으로 이동
    }
}
