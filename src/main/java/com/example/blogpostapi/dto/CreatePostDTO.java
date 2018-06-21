package com.example.blogpostapi.dto;

public class CreatePostDTO {

    private String title;
    private String body;

    public CreatePostDTO() {
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
}
