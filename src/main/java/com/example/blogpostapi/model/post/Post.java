package com.example.blogpostapi.model.post;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private long postId;

    @NotNull
    private String title;

    @NotNull
    private String body;

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return postId == post.postId &&
                title.equals(post.title) &&
                body.equals(post.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, title, body);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                "title=" + title +
                "body=" + body +
                '}';
    }

    public PostBuilder toBuilder() {
        return newBuilder(this);
    }

    public static PostBuilder newBuilder() {
        return new PostBuilder();
    }

    public static PostBuilder newBuilder(Post source) {
        return new PostBuilder()
                .withTitle(source.getTitle())
                .withBody(source.getBody());
    }

    public static final class PostBuilder {
        private String title;
        private String body;

        public PostBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public PostBuilder but() {
            return newBuilder().withBody(body).withTitle(title);
        }

        public Post build() {
            return new Post(title, body);
        }
    }
}