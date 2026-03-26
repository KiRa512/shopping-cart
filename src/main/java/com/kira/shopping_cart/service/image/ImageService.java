package com.kira.shopping_cart.service.image;

import com.kira.shopping_cart.dto.ImageDTO;
import com.kira.shopping_cart.dto.ProductDTO;
import com.kira.shopping_cart.model.Image;
import com.kira.shopping_cart.model.Product;
import com.kira.shopping_cart.repo.ImageRepo;
import com.kira.shopping_cart.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepo imageRepo;
    private final ProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(
                imageRepo::delete,
                () -> { throw new RuntimeException("Image not found with id: " + id); });

    }

    @Override
    public List<ImageDTO> saveImages(Long productId, List<MultipartFile> files) {
        ProductDTO product = productService.getProductById(productId);
        Product productEntity = productService.getProductEntityById(productId);
        List<ImageDTO> savedImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try {

                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(productEntity);

                Image savedImage = imageRepo.save(image);

                String downloadUrl = "/api/v1/images/image/download/" + savedImage.getId();
                savedImage.setDownloadUrl(downloadUrl);

                imageRepo.save(savedImage);

                ImageDTO imageDto = new ImageDTO(
                        savedImage.getId(),
                        savedImage.getFileName(),
                        savedImage.getDownloadUrl()
                );

                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
