package com.feliciano.lantern;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // You can add custom query methods here if needed
}
