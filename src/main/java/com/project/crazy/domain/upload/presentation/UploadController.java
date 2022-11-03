package com.project.crazy.domain.upload.presentation;

import com.project.crazy.domain.upload.service.UploadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private final UploadService uploadService;

    @ApiOperation("첨부파일을 업로드합니다")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/attachments")
    public Long uploadAttachment(@RequestParam("preview") MultipartFile file) {
        return uploadService.uploadAttachment(file);
    }

    @ApiOperation("첨부파일을 가져옵니다")
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/attachments/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getAttachment(@PathVariable("id") Long id) {
        return uploadService.getAttachment(id);
    }

}
