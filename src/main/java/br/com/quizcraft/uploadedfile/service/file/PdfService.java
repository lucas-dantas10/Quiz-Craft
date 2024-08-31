package br.com.quizcraft.uploadedfile.service.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class PdfService {

    public List<Map<String, Object>> extractQuestionTextFromPdf(MultipartFile exam) throws IOException {
        String questions = this.getTextFromPdf(exam);

        List<Map<String, Object>> questionsMap = new ArrayList<>();
        questions = questions.replaceAll("(?i)\\b(caderno\\s*\\w+|1º DIA|.*fragmento).*\\R?", "");
        String[] questionSplit = questions.split("QUESTÃO \\d+");

        for (String question : questionSplit) {
            if (question.isEmpty()) continue;

            Map<String, Object> questionMap = new HashMap<>();
            String[] lines = question.split("\\R");
            StringBuilder questionTextBuilder = new StringBuilder();
            List<Map<String, String>> options = new ArrayList<>();
            boolean isFirstLine = true;

            for (String line : lines) {
                if (isFirstLine) {
                    questionTextBuilder.append(line.trim()).append(" ");
                    isFirstLine = false;
                    continue;
                }

                if (line.matches("^[A-E]\\s+.*")) {
                    Map<String, String> option = new HashMap<>();
                    option.put("option", line.substring(0, 1));
                    option.put("text", line.substring(2).trim());
                    options.add(option);
                } else {
                    questionTextBuilder.append(line.trim()).append(" ");
                }
            }

            String questionText = questionTextBuilder.toString().trim();
            questionMap.put("question_text", questionText);
            questionMap.put("options", options);

            questionsMap.add(questionMap);
        }

        return questionsMap;
    }

    public void extractAnswersTextFromPdf(MultipartFile answers) throws IOException {
        String answersText = this.getTextFromPdf(answers);

        // TODO: format text and save in DTO and return DTO
    }

    private String getTextFromPdf(MultipartFile file) {
        String text = "";

        log.info("Extracting text from: {}", file.getOriginalFilename());

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            if (document.getNumberOfPages() > 1) {
                document.removePage(0);
            }

            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            text = pdfTextStripper.getText(document);
            text = text.replaceAll("(?m)^(LINGUAGENS, CÓDIGOS E SUAS TECNOLOGIAS|CIÊNCIAS HUMANAS " +
                "E SUAS TECNOLOGIAS|Questões de \\d+ a \\d+|Questões de \\d+ a \\d+ \\(opção [a-zA-Z]+\\)" +
                "|\\d+\\s*–LC\\s*•).*\\R?", "");
        } catch (final Exception e) {
            log.error("Error parsing PDF", e);
        }

        log.info("Extracted text");

        return text;
    }
}
