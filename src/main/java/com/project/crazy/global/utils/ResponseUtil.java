package com.project.crazy.global.utils;

import com.project.crazy.domain.post.entity.Post;
import com.project.crazy.domain.post.presentation.dto.response.PostResponse;

import java.time.format.DateTimeFormatter;

public class ResponseUtil {

    public static PostResponse getPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .like(post.getLove())
                .location(post.getLocation())
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(post.getCreatedAt()))
                .build();
    }

}
