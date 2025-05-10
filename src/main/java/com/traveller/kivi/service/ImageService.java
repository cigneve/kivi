package com.traveller.kivi.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import com.traveller.exception.ImageNotFoundException;
import com.traveller.kivi.model.Image;
import com.traveller.kivi.repository.ImageRepository;
import com.traveller.kivi.repository.ImageStore;

@Service
public class ImageService {

    private static final InputStream defaultPhotoResource = ImageService.class
            .getResourceAsStream("/static/default-profile.png");

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageStore imageStore;

    public boolean imageExistsById(Integer id) {
        return imageRepository.existsById(id);
    }

    /**
     * Throws a runtime exception of no such element exists
     * 
     * @param id
     * @return
     */
    public Image getImageById(Integer id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
    }

    public InputStream getImageContent(Image image) {
        return imageStore.getContent(image);
    }

    public void setImageContent(Image image, InputStreamResource res) {
        imageStore.setContent(image, res);
    }

    public InputStreamResource getImageContentAsResource(Image image) {
        return new InputStreamResource(getImageContent(image));
    }

    public Image createImage(InputStream stream) {
        Image image = new Image(UUID.randomUUID().toString());
        imageRepository.save(image);
        imageStore.setContent(image, stream);
        return image;
    }

    public Image getDefaultImage() {
        final String contentId = "default-profile";
        return imageRepository
                .findByContentId(contentId)
                .orElseGet(() -> {
                    Image img = new Image(contentId);
                    img = imageRepository.save(img);
                    imageStore.setContent(img, new InputStreamResource(defaultPhotoResource));
                    return img;
                });
    }
}
