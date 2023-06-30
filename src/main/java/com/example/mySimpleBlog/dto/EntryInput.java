package com.example.mySimpleBlog.dto;

import java.util.List;

public class EntryInput {

    private String title;
    private String text;
    private byte[] imageData;
    private String imageContentType;
    private List<String> tags;

    public EntryInput() {
    }

    public EntryInput(String title, String text, byte[] imageData, String imageContentType, List<String> tags) {
        this.title = title;
        this.text = text;
        this.imageData = imageData;
        this.imageContentType = imageContentType;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "EntryInput{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", imageContentType='" + imageContentType + '\'' +
                ", tags=" + tags +
                '}';
    }
}
