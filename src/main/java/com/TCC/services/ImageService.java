package com.TCC.services;

import com.TCC.domain.image.Image;
import com.TCC.repositories.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public Image uploadImage(MultipartFile file) {
        Image image = new Image();

        try {
            image.setName(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setPicByte(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return imageRepository.save(image);
    }

    public void deleteImage(Image image) {
        imageRepository.delete(image);
    }
}
