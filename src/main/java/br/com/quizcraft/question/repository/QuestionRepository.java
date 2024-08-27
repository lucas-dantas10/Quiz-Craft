package br.com.quizcraft.question.repository;

import br.com.quizcraft.question.entity.Question;
import br.com.quizcraft.question.enums.AnswerEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_questions (question_id, correct_answer, options, question_text) " +
        "VALUES (uuid_generate_v4(), :correctAnswer, to_json(:options), :questionText)", nativeQuery = true)
    void insertQuestion(@Param("correctAnswer") String correctAnswer,
                @Param("options") String options,
                @Param("questionText") String questionText);
}
