package com.project.crazy.domain.post.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class CreatePostRequest {

    private String title;
    private String content;
    private String location;

}
