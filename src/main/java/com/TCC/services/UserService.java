package com.TCC.services;

import com.TCC.domain.company.Company;
import com.TCC.domain.customer.Customer;
import com.TCC.domain.image.Image;
import com.TCC.domain.user.User;
import com.TCC.domain.user.UserProfileDTO;
import com.TCC.repositories.CompanyRepository;
import com.TCC.repositories.CustomerRepository;
import com.TCC.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final ImageService imageService;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository, CompanyRepository companyRepository, ImageService imageService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.imageService = imageService;
    }

    public User findUserById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    public User findUserByEmail(String email) {
        return (User) this.userRepository.findByEmail(email);
    }

    public UserProfileDTO getUserProfile(String id) {
        User user = this.findUserById(id);
        Customer customer = customerRepository.findByUserId(user.getId());

        if (customer != null) {
            return new UserProfileDTO(user.getEmail(), customer.getName(), user.getImage(), user.getRole(), customer.getAddress());
        } else {
            Company company = companyRepository.findByUserId(user.getId());
            return new UserProfileDTO(user.getEmail(), company.getName(), user.getImage(), user.getRole(), company.getAddress());
        }
    }

    public void uploadPhoto(String userId, MultipartFile file) {
        User user = this.findUserById(userId);

        Image image = user.getImage();

        if (image != null) {
            user.setImage(null);
            imageService.deleteImage(image);
        }

        user.setImage(imageService.uploadImage(file));

        userRepository.save(user);
    }

    public void removePhoto(String userId) {
        User user = this.findUserById(userId);

        Image image = user.getImage();

        if (image != null) {
            user.setImage(null);
            imageService.deleteImage(image);
        }

        userRepository.save(user);
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists with this email.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

        return userRepository.save(new User(user.getEmail(), encryptedPassword, user.getRole(), user.getHasGoogleAuth()));
    }
}