package br.com.quizcraft.uploadedfile.service.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class PdfService {

    public void extractTextFromPdf(MultipartFile exam) throws IOException {
        String questions = "";

        try (PDDocument document = PDDocument.load(exam.getInputStream())) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            questions = pdfTextStripper.getText(document);
        } catch (final Exception e) {
            log.error("Error parsing PDF", e);
        }

        // TODO: extract text from answers

        // TODO: format text both and save in DTO

        // TODO: think how return two DTOs or change method
    }
}
