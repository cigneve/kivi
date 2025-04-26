package com.traveller.kivi.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import com.traveller.kivi.model.Image;
import com.traveller.kivi.repository.ImageRepository;
import com.traveller.kivi.repository.ImageStore;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageStore imageStore;

    public boolean imageExistsById(String id) {
        return imageRepository.existsById(id);
    }

    /**
     * Throws a runtime exception of no such element exists
     * 
     * @param id
     * @return
     */
    public Image getImageById(String id) {
        return imageRepository.findById(id).get();
    }

    public InputStream getImageContent(String id) {
        Image image = getImageById(id);
        return imageStore.getContent(image);
    }

    public InputStreamResource getImageContentAsResource(String id) {
        return new InputStreamResource(getImageContent(id));
    }

    public Image createImage(String id, String stream) {

        Image image = new Image(id);
        imageRepository.save(image);
        imageStore.setContent(image, new ByteArrayInputStream(stream.getBytes()));
        return image;
    }
}
