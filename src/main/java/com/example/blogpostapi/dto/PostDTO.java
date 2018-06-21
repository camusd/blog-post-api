package com.example.blogpostapi.dto;

public class PostDTO {

    private long postId;
    private String title;
    private String body;

    public PostDTO() {
    }

    public PostDTO(long postId, String title, String body) {
        this.postId = postId;
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

    public static PostDTOBuilder newBuilder() {
        return new PostDTOBuilder();
    }

    public static final class PostDTOBuilder {

        private long postId;
        private String title;
        private String body;

        public PostDTOBuilder withPostId(long postId) {
            this.postId = postId;
            return this;
        }

        public PostDTOBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public PostDTOBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public PostDTO build() {
            return new PostDTO(postId, title, body);
        }

    }
}
