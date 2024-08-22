package br.com.quizcraft.question.entity;

import br.com.quizcraft.question.enums.AnswerEnum;
import br.com.quizcraft.entity.Questionnaire;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_questions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "question_id", nullable = false)
    private UUID questionId;

    @ManyToOne
    @JoinColumn(name = "questionnaire_id", nullable = false)
    private Questionnaire questionnaireId;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String options;

    @Column(name = "correct_answer", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum correctAnswer;
}
