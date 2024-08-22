package br.com.quizcraft.uploadedfile.repository;

import br.com.quizcraft.uploadedfile.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UploadFileRepository extends JpaRepository<UploadedFile, UUID> {
}
