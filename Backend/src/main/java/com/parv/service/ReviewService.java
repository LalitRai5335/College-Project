package com.parv.service;

import com.parv.entity.Review;
import com.parv.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAllApprovedReviews() {
        return reviewRepository.findByIsApprovedTrue();
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review approveReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        review.setApproved(true);
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }
}
