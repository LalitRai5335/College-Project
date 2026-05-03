package com.parv.repository;

import com.parv.entity.TeaProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<TeaProduct, Long> {
    List<TeaProduct> findByIsActiveTrue();
}
