package com.project.crazy.domain.post.presentation;

import com.project.crazy.domain.post.presentation.dto.request.CreatePostRequest;
import com.project.crazy.domain.post.presentation.dto.response.CreatePostResponse;
import com.project.crazy.domain.post.presentation.dto.response.PostListResponse;
import com.project.crazy.domain.post.presentation.dto.response.PostResponse;
import com.project.crazy.domain.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @ApiOperation("게시글 만들어")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public CreatePostResponse createPost(
            @RequestBody CreatePostRequest request
    ) {
        return postService.createPost(request);
    }

    @ApiOperation("게시글 목록 불러와")
    @GetMapping("/list")
    public PostListResponse getAllPost() {
        return postService.getAllPost();
    }

    @ApiOperation("게시글 정보 불러와")
    @GetMapping("/{post-id}")
    public PostResponse getPostById(
            @PathVariable("post-id") Long postId
    ) {
        return postService.getPostById(postId);
    }

    @ApiOperation("게시글 좋아요 올리기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/increase/{post-id}")
    public void increaseLike(
            @PathVariable("post-id") Long postId
    ) {
        postService.increaseLike(postId);
    }

    @ApiOperation("게시글 좋아요 내리기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/decrease/{post-id}")
    public void decreaseLike(
            @PathVariable("post-id") Long postId
    ) {
        postService.decreaseLike(postId);
    }

}
