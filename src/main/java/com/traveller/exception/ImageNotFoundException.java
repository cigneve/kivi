package com.traveller.exception;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(Integer imageId) {
        super("Image with id " + imageId + " not found");
    }

}
