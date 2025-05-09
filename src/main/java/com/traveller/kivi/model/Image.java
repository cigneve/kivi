package com.traveller.kivi.model;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private Integer id;

    @ContentId
    private String contentId;

    @ContentLength
    @Column(unique = true)
    private long contentLength;

    private String contentMimeType = "image/png";

    public Image(String contentId) {
        this.contentId = contentId;
    }

    protected Image() {
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public void setId(Integer id) {
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

    public Integer getId() {
        return id;
    }

}
