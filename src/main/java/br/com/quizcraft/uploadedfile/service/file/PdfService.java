package br.com.quizcraft.uploadedfile.service.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PdfService {

    public void extractQuestionTextFromPdf(MultipartFile exam) throws IOException {
        String questions = this.getTextFromPdf(exam);

        List<Map<String, Object>> questionsMap = new ArrayList<>();
        String[] questionSplit = questions.split("QUESTÃO \\d+");

        for (String question : questionSplit) {
            if (question.isEmpty()) continue;

            Map<String, Object> questionMap = new HashMap<>();
            Pattern patternOption = Pattern.compile("\"([A-E])\\\\s(.+?)(?=\\\\n[A-E]\\\\s|$)\"", Pattern.DOTALL);
            Matcher matcherOption = patternOption.matcher(question);

            List<String> options = new ArrayList<>();
            int startOptionsIndex = question.length();

            while (matcherOption.find()) {
                options.add(matcherOption.group(1) + ": " + matcherOption.group(2).trim());
                startOptionsIndex = Math.min(startOptionsIndex, matcherOption.start());
            }

            String questionText = question.substring(0, startOptionsIndex).trim();
            questionMap.put("Enunciado", questionText);
            questionMap.put("Opções", options);

            questionsMap.add(questionMap);
        }

        // TODO: separate QUESTION and OPTIONS correctly

        for (int i = 0; i < questionsMap.size(); i++) {
            log.info("QUESTÃO: {}", (i + 1));
            log.info("Enunciado: {}", questionsMap.get(i).get("Enunciado"));
            List<String> opcoes = (List<String>) questionsMap.get(i).get("Opções");
            for (String opcao : opcoes) {
                log.info(opcao);
            }
        }
        // TODO: save in DTO and return DTO
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

        log.info("Extracted text from PDF: {}", text);
        return text;
    }
}
