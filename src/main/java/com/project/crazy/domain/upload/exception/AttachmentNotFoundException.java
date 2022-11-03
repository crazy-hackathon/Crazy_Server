package com.project.crazy.domain.upload.exception;

import com.project.crazy.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AttachmentNotFoundException extends BusinessException {

    public static final AttachmentNotFoundException EXCEPTION = new AttachmentNotFoundException();

    private AttachmentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "첨부파일을 찾을 수 없습니다");
    }
}
