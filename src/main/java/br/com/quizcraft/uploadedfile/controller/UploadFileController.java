package br.com.quizcraft.uploadedfile.controller;

import br.com.quizcraft.uploadedfile.service.uploadfile.UploadFileServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadFileController {

    @Autowired
    private UploadFileServiceInterface uploadFileService;

    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam(name = "exam") MultipartFile exam,
            @RequestParam(name = "answers") MultipartFile answers
            ) throws IOException {

        uploadFileService.uploadFile(exam, answers);

        return ResponseEntity.ok().build();
    }
}
