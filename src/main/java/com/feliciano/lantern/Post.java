package com.feliciano.lantern;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(name = "image_url")  // Changed to match URL storage
    private String imageUrl;     // Renamed from imagePath to reflect URL usage

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    // ================ CONSTRUCTORS ================
    public Post() {}

    // ================ GETTERS/SETTERS ================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @deprecated Use getImageUrl() instead
     */
    @Deprecated
    public String getImagePath() {
        return imageUrl;  // Returns the same value for backward compatibility
    }

    /**
     * @deprecated Use setImageUrl() instead
     */
    @Deprecated
    public void setImagePath(String imagePath) {
        this.imageUrl = imagePath;  // Maintains backward compatibility
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}