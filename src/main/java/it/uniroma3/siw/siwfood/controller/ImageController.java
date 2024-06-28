package it.uniroma3.siw.siwfood.controller;

import it.uniroma3.siw.siwfood.model.Image;
import it.uniroma3.siw.siwfood.service.foodservices.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        Optional<Image> image = imageService.getImageById(id);
        if (image.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg\"")
                    .body(image.get().getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestParam("file") MultipartFile file) {
        try {
            Image image = new Image();
            image.setData(file.getBytes());
            Image savedImage = imageService.saveImage(image);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<byte[]> getImageByUserId(@PathVariable Long userId) {
        Optional<Image> image = imageService.getImageByUserId(userId);
        if (image.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"user_" + userId + ".jpg\"")
                    .body(image.get().getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/upload/{userId}")
    public ResponseEntity<Image> uploadImageForUser(@PathVariable Long userId,
                                                    @RequestParam("file") MultipartFile file) {
        try {
            Image savedImage = imageService.saveImageForUser(userId, file);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload/recipe/{recipeId}")
    public ResponseEntity<Image> uploadImageForRecipe(@PathVariable Long recipeId,
                                                    @RequestParam("file") MultipartFile file) {
        try {
            Image savedImage = imageService.saveImageForRicetta(file, recipeId);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
