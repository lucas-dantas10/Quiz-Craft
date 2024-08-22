package br.com.quizcraft.uploadedfile.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@WebMvcTest
class UploadFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final MockMultipartFile examFile;
    private final MockMultipartFile answersFile;

    public UploadFileControllerTest() {
        examFile = new MockMultipartFile(
            "exam",
            "exam.pdf",
            MediaType.APPLICATION_PDF_VALUE,
            UploadFileControllerTest.class.getResource("/exam/exam.pdf")
                .getPath()
                .getBytes()
        );

        answersFile = new MockMultipartFile(
            "answers",
            "answer.pdf",
            MediaType.APPLICATION_PDF_VALUE,
            UploadFileControllerTest.class.getResource("/answer/answer.pdf")
                .getPath()
                .getBytes()
        );
    }

    @Test
    @DisplayName("Should upload file")
    void shouldUploadFile() throws Exception {
        // TODO: add new requires of the endpoint
        mockMvc.perform(
                multipart("/upload")
                    .file(examFile)
                    .file(answersFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not upload file because content type is not provided")
    void shouldUploadFileBecauseContentTypeIsNotProvided() throws Exception {
        mockMvc.perform(
                multipart("/upload")
                    .file(examFile)
                    .file(answersFile)
                    .contentType(MediaType.TEXT_PLAIN)
            )
            .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Should not upload file because param 'exam' is not provided")
    void shouldNotUploadFileBecauseParamExamIsNotProvided() throws Exception {
        mockMvc.perform(
                multipart("/upload")
                    .file(answersFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should not upload file because param 'answers' is not provided")
    void shouldNotUploadFileBecauseParamAnswersIsNotProvided() throws Exception {
        mockMvc.perform(
                multipart("/upload")
                    .file(examFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}