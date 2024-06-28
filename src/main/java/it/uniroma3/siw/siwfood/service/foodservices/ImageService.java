package it.uniroma3.siw.siwfood.service.foodservices;

import it.uniroma3.siw.siwfood.model.Image;
import it.uniroma3.siw.siwfood.model.Ricetta;
import it.uniroma3.siw.siwfood.repository.RicettaRepository;
import it.uniroma3.siw.siwfood.repository.UserRepository;
import it.uniroma3.siw.siwfood.model.User;
import it.uniroma3.siw.siwfood.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {


    private final RicettaRepository ricettaRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageService(UserRepository userRepository, ImageRepository imageRepository, RicettaRepository ricettaRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.ricettaRepository = ricettaRepository;
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    public Optional<Image> getImageByUserId(Long userId) {
        return imageRepository.findByUserId(userId);
    }

    public Optional<Image> getImageByRicettaId(Long ricettaId) {
        return imageRepository.findByRicettaId(ricettaId);
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public Image saveImageForUser(Long userId, MultipartFile file) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Image image = new Image();
            image.setData(file.getBytes());
            image.setUser(user);

            return imageRepository.save(image);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Image saveImageForRicetta(MultipartFile file, Long ricettaId) throws IOException {
        Optional<Ricetta> ricettaOptional = ricettaRepository.findById(ricettaId);
        if (ricettaOptional.isPresent()) {
            Ricetta ricetta = ricettaOptional.get();

            Image image = new Image();
            image.setData(file.getBytes());
            image.setRicetta(ricetta);

            return imageRepository.save(image);
        } else {
            throw new RuntimeException("Ricetta not found");
        }
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}

