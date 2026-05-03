package com.parv.service;

import com.parv.entity.TeaProduct;
import com.parv.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${server.base-url}")
    private String baseUrl;

    public List<TeaProduct> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue().stream()
                .map(this::enrichImageUrl)
                .collect(Collectors.toList());
    }

    public List<TeaProduct> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::enrichImageUrl)
                .collect(Collectors.toList());
    }

    public TeaProduct getProductById(Long id) {
        TeaProduct product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return enrichImageUrl(product);
    }

    private TeaProduct enrichImageUrl(TeaProduct product) {
        if (product.getImageUrl() != null && !product.getImageUrl().startsWith("http")) {
            product.setImageUrl(baseUrl + product.getImageUrl());
        }
        return product;
    }

    public TeaProduct saveProduct(TeaProduct product) {
        TeaProduct savedProduct = productRepository.save(product);
        return enrichImageUrl(savedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public long countTotalProducts() {
        return productRepository.count();
    }
}
