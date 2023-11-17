package com.example.sample.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentCreateForm {
    @NotBlank(message = "名前の入力は必須です")
    @Size(max = 20, message = "名前は最大20文字です")
    private String name;

    @NotBlank(message = "コメントの入力は必須です")
    @Size(max = 100, message = "コメントは最大100文字です")
    private String content;
}
