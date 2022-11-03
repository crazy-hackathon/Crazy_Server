package com.project.crazy.domain.post.entity;

import com.project.crazy.domain.auth.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    public void setAuthor(User author) {
        this.author = author;
    }

    private String title;

    private String content;

    private String location;

    @ColumnDefault("0")
    private int like;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Post(String title, String content, String location, int like) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.like = like;
    }
}
