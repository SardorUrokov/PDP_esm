package com.example.pdp_esm.service.Implements;

import com.example.pdp_esm.dto.result.ApiResponse;
import com.example.pdp_esm.entity.Attachment;
import com.example.pdp_esm.service.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.pdp_esm.repository.AttachmentRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final AttachmentRepository attachmentRepository;

    public ApiResponse<?> uploadFile(MultipartFile file) throws IOException {
        Attachment imageData = attachmentRepository.save(Attachment.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .bytes(FileUtils.compressFile(file.getBytes())).build());
        if (imageData != null) {
            return new ApiResponse<>("file uploaded successfully : " + file.getOriginalFilename(), true);
        }
        return null;
    }

    public byte[] downloadFile(String fileName){
        Optional<Attachment> dbImageData = attachmentRepository.findByName(fileName);
        return FileUtils.decompressFile(dbImageData.get().getBytes());
    }
}
