package br.com.quizcraft.uploadedfile.service.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Pattern patternOption = Pattern.compile("(?m)^(A|B|C|D|E)\\s+(.+?)(?=^\\s*[A-E]\\s|\\Z)", Pattern.DOTALL);
            Matcher matcherOption = patternOption.matcher(question);

            List<Map<String, String>> options = new ArrayList<>();
            int startOptionsIndex = question.length();

            while (matcherOption.find()) {
                Map<String, String> option = new HashMap<>();
                option.put("option", matcherOption.group(1));
                option.put("text", matcherOption.group(2).trim());
                options.add(option);
                startOptionsIndex = Math.min(startOptionsIndex, matcherOption.start());
            }

            String questionText = question.substring(0, startOptionsIndex).trim();
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
            text = text.replaceAll("(?m)^(LINGUAGENS, CÓDIGOS E SUAS TECNOLOGIAS|CIÊNCIAS HUMANAS E SUAS TECNOLOGIAS|Questões de \\d+ a \\d+|Questões de \\d+ a \\d+ \\(opção [a-zA-Z]+\\)|\\d+\\s*–LC\\s*•).*\\R?", "");
        } catch (final Exception e) {
            log.error("Error parsing PDF", e);
        }

        log.info("Extracted text");

        return text;
    }
}
