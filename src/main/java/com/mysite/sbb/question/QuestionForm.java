package com.mysite.sbb.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    //NotBlank: space도 검사해 줌.
    //NotEmpty: space는 허락해 줌.
    @NotBlank(message="제목은 필수항목입니다.")
    @Size(max=200, message = "제목은 최대 200자까지 가능합니다.") //최대 200까지 가능
    private String subject;

    @NotBlank(message="내용은 필수항목입니다.")
    @Size(max=20_000, message = "내용은 최대 20,000자까지 가능합니다.")
    private String content;
}
