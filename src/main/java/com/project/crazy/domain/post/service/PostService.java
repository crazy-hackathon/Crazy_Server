package com.project.crazy.domain.post.service;

import com.project.crazy.domain.auth.entity.User;
import com.project.crazy.domain.auth.facade.UserFacade;
import com.project.crazy.domain.post.entity.Post;
import com.project.crazy.domain.post.exception.PostNotFoundException;
import com.project.crazy.domain.post.presentation.dto.request.CreatePostRequest;
import com.project.crazy.domain.post.presentation.dto.response.CreatePostResponse;
import com.project.crazy.domain.post.presentation.dto.response.PostListResponse;
import com.project.crazy.domain.post.presentation.dto.response.PostResponse;
import com.project.crazy.domain.post.repository.PostRepository;
import com.project.crazy.domain.upload.entity.PostAttachment;
import com.project.crazy.domain.upload.exception.AttachmentNotCoincidenceException;
import com.project.crazy.domain.upload.repository.PostAttachmentRepository;
import com.project.crazy.global.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostAttachmentRepository postAttachmentRepository;
    private final UserFacade userFacade;

    @Transactional(rollbackFor = Exception.class)
    public CreatePostResponse createPost(CreatePostRequest request) {
        User author = userFacade.queryUser(true);

        Post post = Post.builder()
                .title(request.getTitle()).content(request.getContent())
                .location(request.getLocation())
                .build();
        author.addPost(post);

        if(!request.getAttachments().isEmpty()) {
            List<PostAttachment> postAttachments = request.getAttachments().stream().map(id -> {
                return postAttachmentRepository.findById(id).orElseThrow(() -> {throw AttachmentNotCoincidenceException.EXCEPTION;});
            }).peek(attachment -> attachment.setPost(post)).collect(Collectors.toList());
            post.addAttachments(postAttachments);
        }

        return new CreatePostResponse(post.getPostId());
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PostListResponse getAllPost() {
        List<Post> list = postRepository.findAll();

        return new PostListResponse(
                list.stream().map(ResponseUtil::getPostResponse).collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {throw PostNotFoundException.EXCEPTION;});

        return ResponseUtil.getPostResponse(post);
    }

}
