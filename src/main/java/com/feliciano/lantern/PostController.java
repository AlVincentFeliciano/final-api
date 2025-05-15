package com.feliciano.lantern.controller;

import com.feliciano.lantern.Post;
import com.feliciano.lantern.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    @Autowired
    private PostService postService;

    // ==================== GET ENDPOINTS ====================
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ==================== SINGLE POST CREATION ====================
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        Post createdPost = postService.createPost(
                request.getContent(),
                request.getImageURL() // Can be null or empty
        );
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // ==================== BULK CREATION ====================
    @PostMapping(path = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> createBulkPosts(@RequestBody List<PostRequest> requests) {
        List<Post> createdPosts = requests.stream()
                .map(request -> postService.createPost(
                        request.getContent(),
                        request.getImageURL()
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(createdPosts, HttpStatus.CREATED);
    }

    // ==================== UPDATE ====================
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestBody PostRequest request) {
        Optional<Post> updatedPost = postService.updatePost(
                id,
                request.getContent(),
                request.getImageURL()
        );
        return updatedPost.map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ==================== DELETE ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return postService.deletePost(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ==================== DTOs ====================
    static class PostRequest {
        private String content;
        private String imageURL; // Matches your exact JSON field name

        // Getters and setters
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }
    }
}