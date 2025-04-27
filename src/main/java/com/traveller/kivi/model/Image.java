package com.traveller.kivi.model;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Image {
    @Id
    @ContentId
    private String id;

    @ContentLength
    private long contentLength;

    private String contentMimeType = "text/plain";

    public Image(String id) {
        this.id = id;
    }

    protected Image() {
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getContentLength() {
        return contentLength;
    }

    public String getContentMimeType() {
        return contentMimeType;
    }

    public void setContentMimeType(String contentMimeType) {
        this.contentMimeType = contentMimeType;
    }

    public String getId() {
        return id;
    }

}
