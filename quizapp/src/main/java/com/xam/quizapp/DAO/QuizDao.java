package com.xam.quizapp.DAO;

import com.xam.quizapp.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
