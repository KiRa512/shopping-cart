package com.kira.shopping_cart.service.image;

import com.kira.shopping_cart.dto.ImageDTO;
import com.kira.shopping_cart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,  Long imageId);
}