package com.parv.repository;

import com.parv.entity.TeaBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeaBenefitRepository extends JpaRepository<TeaBenefit, Long> {
}
