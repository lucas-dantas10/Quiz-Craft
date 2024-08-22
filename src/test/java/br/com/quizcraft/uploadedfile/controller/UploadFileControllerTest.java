package br.com.quizcraft.uploadedfile.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class UploadFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should upload file")
    void shouldUploadFile() throws Exception {
        mockMvc.perform(post("/upload")
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not upload file because content type is not provided")
    void shouldUploadFileBecauseContentTypeIsNotProvided() throws Exception {
        mockMvc.perform(post("/upload"))
                .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }

    @Test
    @DisplayName("Should not upload file because file is not provided")
    void shouldNotUploadFile() {
        // TODO
    }

    @Test
    @DisplayName("Should not upload file because file is not correct type")
    void shouldNotUploadFileBecauseFileIsNotCorrectType() {
        // TODO
    }
}