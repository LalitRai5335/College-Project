package com.parv.config;

import com.parv.entity.Review;
import com.parv.entity.TeaProduct;
import com.parv.repository.ProductRepository;
import com.parv.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            TeaProduct p1 = TeaProduct.builder()
                    .title("Classic Masala Chai")
                    .subtitle("Traditional Indian Spice Blend")
                    .description("A rich blend of black tea with aromatic spices like cardamom, ginger, and cloves.")
                    .price(499.0)
                    .category("Masala Chai")
                    .isActive(true)
                    .imageUrl("https://images.unsplash.com/photo-1594631252845-29fc4586c562?auto=format&fit=crop&q=80&w=400")
                    .build();

            TeaProduct p2 = TeaProduct.builder()
                    .title("Premium Green Tea")
                    .subtitle("Pure Himalayan Leaves")
                    .description("Handpicked green tea leaves from the high altitudes of the Himalayas.")
                    .price(599.0)
                    .category("Green Tea")
                    .isActive(true)
                    .imageUrl("https://images.unsplash.com/photo-1627435601361-ec25f5b1d0e5?auto=format&fit=crop&q=80&w=400")
                    .build();

            TeaProduct p3 = TeaProduct.builder()
                    .title("Assam Strong Black")
                    .subtitle("Bold & Malty")
                    .description("Full-bodied black tea from the lush gardens of Assam, perfect for a morning boost.")
                    .price(399.0)
                    .category("Black Tea")
                    .isActive(true)
                    .imageUrl("https://images.unsplash.com/photo-1597481499750-3e6b22637e12?auto=format&fit=crop&q=80&w=400")
                    .build();

            productRepository.saveAll(Arrays.asList(p1, p2, p3));
        }

        if (reviewRepository.count() == 0) {
            Review r1 = Review.builder()
                    .reviewerName("Rahul Sharma")
                    .rating(5)
                    .comment("The Masala Chai is absolutely authentic. Reminds me of home!")
                    .isApproved(true)
                    .build();

            Review r2 = Review.builder()
                    .reviewerName("Priya Patel")
                    .rating(4)
                    .comment("Great quality green tea. Very refreshing.")
                    .isApproved(true)
                    .build();

            reviewRepository.saveAll(Arrays.asList(r1, r2));
        }
    }
}

