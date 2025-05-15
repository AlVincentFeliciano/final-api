package com.feliciano.lantern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    // ================ GET METHODS ================
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // ================ CREATE (URL-ONLY) ================
    public Post createPost(String content, String imageURL) {
        Post post = new Post();
        post.setContent(content);
        post.setImageUrl(imageURL); // Now stores URL directly
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    // ================ UPDATE (URL-ONLY) ================
    public Optional<Post> updatePost(Long id, String content, String imageURL) {
        return postRepository.findById(id).map(post -> {
            post.setContent(content);
            post.setImageUrl(imageURL); // Update with new URL
            post.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(post);
        });
    }

    // ================ DELETE ================
    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ================ (KEPT FOR REFERENCE) FILE UPLOAD METHODS ================
    /*
    @Deprecated
    public Post createPost(String content, MultipartFile image) {
        Post post = new Post();
        post.setContent(content);
        if (image != null && !image.isEmpty()) {
            post.setImageUrl(saveImage(image)); // Would need modification for URL storage
        }
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Deprecated
    private String saveImage(MultipartFile image) {
        try {
            Path uploadDir = Paths.get(uploadDirectory);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            String uniqueFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = uploadDir.resolve(uniqueFileName);
            Files.copy(image.getInputStream(), filePath);
            return "/uploads/" + uniqueFileName; // Return accessible URL path
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
    */
}