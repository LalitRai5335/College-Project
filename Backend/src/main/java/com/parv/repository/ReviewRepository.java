package com.parv.repository;

import com.parv.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByIsApprovedTrue();
    List<Review> findByProductIdAndIsApprovedTrue(Long productId);
}
