    package it.uniroma3.siw.siwfood.controller;

    import it.uniroma3.siw.siwfood.model.Image;
    import it.uniroma3.siw.siwfood.response.ImageResponse;
    import it.uniroma3.siw.siwfood.service.foodservices.ImageService;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MaxUploadSizeExceededException;
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



        @PostMapping("/upload/user/{userId}")
        public ResponseEntity<ImageResponse> uploadImageForUser(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
            try {
                Optional<Image> pfp = imageService.getImageByUserId(userId);
                if (pfp.isPresent()) {
                    // Se esiste già un'immagine per l'utente, aggiorna i dati dell'immagine
                    Image profileImage = pfp.get();
                    profileImage.setData(file.getBytes());
                    imageService.saveImage(profileImage); // Salva l'immagine aggiornata

                    // Costruisci e restituisci la risposta con i dati aggiornati dell'immagine
                    ImageResponse response = new ImageResponse(profileImage.getId(), profileImage.getData(),
                            profileImage.getUser().getId(),
                            profileImage.getRicetta() != null ? profileImage.getRicetta().getId() : null);
                    return ResponseEntity.ok(response);
                } else {
                    // Se non esiste un'immagine per l'utente, salva una nuova immagine
                    Image savedImage = imageService.saveImageForUser(userId, file);

                    // Costruisci e restituisci la risposta con i dati della nuova immagine salvata
                    ImageResponse response = new ImageResponse(savedImage.getId(), savedImage.getData(),
                            savedImage.getUser().getId(),
                            savedImage.getRicetta() != null ? savedImage.getRicetta().getId() : null);
                    return ResponseEntity.ok(response);
                }
            } catch (MaxUploadSizeExceededException e) {
                return ResponseEntity.badRequest().build();
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        @PostMapping("/upload/recipe/{recipeId}")
        public ResponseEntity<ImageResponse> uploadImageForRecipe(@PathVariable Long recipeId, @RequestParam("file") MultipartFile file, int index) {
            try {
                Image savedImage = imageService.saveImageForRicetta(file, recipeId, index);
                ImageResponse response = new ImageResponse(savedImage.getId(), savedImage.getData(), savedImage.getRicetta().getId(), savedImage.getUser()  != null ? savedImage.getUser().getId() : null);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                return ResponseEntity.badRequest().build();
            } catch (Exception e) {
                // Log dell'eccezione per identificare la causa del problema
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
            imageService.deleteImage(id);
            return ResponseEntity.noContent().build();
        }
    }
