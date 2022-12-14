package com.project.crazy.domain.post.entity;

import com.project.crazy.domain.auth.entity.User;
import com.project.crazy.domain.upload.entity.PostAttachment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;
    public void setAuthor(User author) {
        this.author = author;
    }

    private String title;

    private String content;

    private String location;

    @ColumnDefault("0")
    private int love;
    public void increaseLike() {
        this.love++;
    }
    public void decreaseLike() {
        if(this.love > 0) this.love--;
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostAttachment> attachmentList;
    public void addAttachments(List<PostAttachment> attachments) {
        attachments.stream().map(it ->
                getAttachmentList().add(it)
        ).close();
    }
    public List<String> getAttachmentUrls() {
        return getAttachmentList().stream()
                .map(attachment -> String.format("/upload/attachments/%d", attachment.getAttachmentId()))
                .collect(Collectors.toList());
    }

    @Builder
    public Post(String title, String content, String location, int love, List<PostAttachment> attachmentList) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.love = love;
        this.attachmentList = attachmentList;
    }
}
