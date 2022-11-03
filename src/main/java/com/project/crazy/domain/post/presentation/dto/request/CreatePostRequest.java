package com.project.crazy.domain.post.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor
public class CreatePostRequest {

    private String title;
    private String content;
    private String location;
    private List<Long> attachments;

}
