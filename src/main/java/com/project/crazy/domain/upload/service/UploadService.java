package com.project.crazy.domain.upload.service;

import com.project.crazy.domain.upload.entity.PostAttachment;
import com.project.crazy.domain.upload.exception.AttachmentFailedSaveException;
import com.project.crazy.domain.upload.exception.AttachmentNotFoundException;
import com.project.crazy.domain.upload.repository.PostAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final PostAttachmentRepository postAttachmentRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public Long uploadAttachment(MultipartFile file) {
        try {
            PostAttachment postAttachment = PostAttachment.builder()
                    .attachmentName(file.getName())
                    .content(file.getBytes())
                    .build();
            postAttachment = postAttachmentRepository.save(postAttachment);

            return postAttachment.getAttachmentId();
        } catch (IOException ex) {
            throw AttachmentFailedSaveException.EXCEPTION;
        }
    }

    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public ResponseEntity<byte[]> getAttachment(Long resourceId) {
        PostAttachment postAttachment = postAttachmentRepository.findById(resourceId)
                .orElseThrow(() -> AttachmentNotFoundException.EXCEPTION);

        String contentDisposition = String.format("attachment; filename=\"%s\"", postAttachment.getAttachmentName());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(postAttachment.getContent());
    }

}
