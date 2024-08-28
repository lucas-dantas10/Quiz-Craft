package br.com.quizcraft.uploadedfile.service.uploadfile;

import br.com.quizcraft.question.builder.OptionBuilder;
import br.com.quizcraft.question.entity.Question;
import br.com.quizcraft.question.enums.AnswerEnum;
import br.com.quizcraft.question.model.Option;
import br.com.quizcraft.question.repository.QuestionRepository;
import br.com.quizcraft.uploadedfile.service.file.PdfService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class UploadFileService implements UploadFileServiceInterface {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void uploadFile(MultipartFile exam, MultipartFile answers) throws IOException {
         List<Map<String, Object>> questionsMap = this.pdfService.extractQuestionTextFromPdf(exam);
         pdfService.extractAnswersTextFromPdf(answers);

        questionsMap.forEach(item -> {
            List<Option> options = OptionBuilder.buildOption(
                (List<Map<String, String>>) item.get("options")
            );
            Question question = Question.builder()
                .questionText((String) item.get("question_text"))
                .options(options)
                .correctAnswer(AnswerEnum.A) // TODO search answer correct
                .build();

            this.questionRepository.save(question);
        });
    }
}
