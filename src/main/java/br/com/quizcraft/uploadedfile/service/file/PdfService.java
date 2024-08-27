package br.com.quizcraft.uploadedfile.service.file;

import br.com.quizcraft.question.dto.QuestionDto;
import br.com.quizcraft.question.entity.Question;
import br.com.quizcraft.question.enums.AnswerEnum;
import br.com.quizcraft.question.repository.QuestionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PdfService {

    @Autowired
    private QuestionRepository questionRepository;

    public void extractQuestionTextFromPdf(MultipartFile exam) throws IOException {
        String questions = this.getTextFromPdf(exam);

        // TODO: Refator code

        List<Map<String, Object>> questionsMap = new ArrayList<>();
        questions = questions.replaceAll("(?i)\\b(caderno\\s*\\w+|1º DIA|.*fragmento).*\\R?", "");
        String[] questionSplit = questions.split("QUESTÃO \\d+");

        for (String question : questionSplit) {
            if (question.isEmpty()) continue;

            Map<String, Object> questionMap = new HashMap<>();
            Pattern patternOption = Pattern.compile("([A-E])\\s(.+?)(?=(?:\\n[A-E]\\s)|$)", Pattern.DOTALL);
            Matcher matcherOption = patternOption.matcher(question);

            List<String> options = new ArrayList<>();
            int startOptionsIndex = question.length();

            while (matcherOption.find()) {
                options.add(matcherOption.group(1) + ": " + matcherOption.group(2).trim());
                startOptionsIndex = Math.min(startOptionsIndex, matcherOption.start());
            }

            String questionText = question.substring(0, startOptionsIndex).trim();
            questionMap.put("question_text", questionText);
            questionMap.put("options", options);

            questionsMap.add(questionMap);
        }

        // TODO: Refator code
        questionsMap.forEach(item -> {
            ObjectMapper mapper = new ObjectMapper();
            String options = "";
            try {
                options = mapper.writeValueAsString(item.get("options"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            Question question = new Question();
            question.setQuestionText((String) item.get("question_text"));
            question.setOptions(options);
            question.setCorrectAnswer(AnswerEnum.A);
            questionRepository.insertQuestion(
                question.getCorrectAnswer().toString(),
                question.getOptions(),
                question.getQuestionText()
            );
        });
    }

    public void extractAnswersTextFromPdf(MultipartFile answers) throws IOException {
        String answersText = this.getTextFromPdf(answers);

        // TODO: format text and save in DTO and return DTO
    }

    private String getTextFromPdf(MultipartFile file) {
        String text = "";

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            text = pdfTextStripper.getText(document);
        } catch (final Exception e) {
            log.error("Error parsing PDF", e);
        }

        return text;
    }
}
