package br.com.quizcraft.uploadedfile.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadFileController {

    @PostMapping(value = "/upload",
            consumes = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity uploadFile() {
        // TODO
        return ResponseEntity.ok().build();
    }
}
