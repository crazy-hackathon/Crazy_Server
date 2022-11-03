package com.project.crazy.domain.upload.entity;

import com.project.crazy.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    public void setPost(Post post) {
        this.post = post;
    }

    private String attachmentName;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

    @Builder
    public PostAttachment(String attachmentName, byte[] content) {
        this.attachmentName = attachmentName;
        this.content = content;
    }
}
