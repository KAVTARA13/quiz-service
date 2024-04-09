package com.example.quizservice.model;

import lombok.Data;

@Data
public class QuestionWrapper {
    private Integer id;

    private String  option1, option2, option3, option4, question_title;
}
