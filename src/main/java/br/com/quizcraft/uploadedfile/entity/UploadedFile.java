package br.com.quizcraft.uploadedfile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_uploaded_file")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uploaded_id", nullable = false)
    private UUID uploadedId;

    @Column(nullable = false)
    private String filename;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "uploaded_at", nullable = false)
    private String uploadedAt;
}
