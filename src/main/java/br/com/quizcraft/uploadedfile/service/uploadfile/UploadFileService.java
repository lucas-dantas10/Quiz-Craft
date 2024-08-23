package br.com.quizcraft.uploadedfile.service.uploadfile;

import br.com.quizcraft.uploadedfile.service.file.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadFileService implements UploadFileServiceInterface {

    @Autowired
    private PdfService pdfService;

    @Override
    public void uploadFile(MultipartFile exam, MultipartFile answers) throws IOException {
        // TODO: Process data of pdf
         pdfService.extractQuestionTextFromPdf(exam);
         pdfService.extractAnswersTextFromPdf(answers);

        // TODO: add texts formatted in database
    }
}
