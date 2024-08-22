package br.com.quizcraft.uploadedfile.service.uploadfile;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileServiceInterface {

    void uploadFile(MultipartFile exam, MultipartFile answers) throws IOException;
}
