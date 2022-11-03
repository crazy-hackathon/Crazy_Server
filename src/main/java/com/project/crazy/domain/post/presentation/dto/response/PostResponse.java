package com.project.crazy.domain.post.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor
@Builder
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String location;
    private int like;
    private String createdAt;

}
