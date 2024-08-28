package br.com.quizcraft.question.entity;

import br.com.quizcraft.question.enums.AnswerEnum;
import br.com.quizcraft.question.model.Option;
import br.com.quizcraft.questionnaire.entity.Questionnaire;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_questions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "question_id", nullable = false)
    private UUID questionId;

//    @ManyToOne
//    @JoinColumn(name = "questionnaire_id", nullable = false)
//    private Questionnaire questionnaireId;

    @Column(name = "question_text", columnDefinition = "TEXT", nullable = false)
    private String questionText;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<Option> options;

    @Column(name = "correct_answer", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum correctAnswer;
}
