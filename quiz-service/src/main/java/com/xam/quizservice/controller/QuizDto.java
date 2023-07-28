package com.xam.quizservice.controller;

import lombok.Data;

@Data
public class QuizDto {
    String category;
    Integer numQ;
    String quizTitle;
}
