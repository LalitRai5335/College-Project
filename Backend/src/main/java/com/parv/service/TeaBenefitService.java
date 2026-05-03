package com.parv.service;

import com.parv.entity.TeaBenefit;
import com.parv.repository.TeaBenefitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeaBenefitService {

    private final TeaBenefitRepository teaBenefitRepository;

    public List<TeaBenefit> getAllBenefits() {
        return teaBenefitRepository.findAll();
    }

    public TeaBenefit getBenefitById(Long id) {
        return teaBenefitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit not found with id: " + id));
    }

    public TeaBenefit createBenefit(TeaBenefit benefit) {
        return teaBenefitRepository.save(benefit);
    }

    public TeaBenefit updateBenefit(Long id, TeaBenefit benefitDetails) {
        TeaBenefit benefit = getBenefitById(id);
        benefit.setTitle(benefitDetails.getTitle());
        benefit.setDescription(benefitDetails.getDescription());
        benefit.setIconName(benefitDetails.getIconName());
        return teaBenefitRepository.save(benefit);
    }

    public void deleteBenefit(Long id) {
        TeaBenefit benefit = getBenefitById(id);
        teaBenefitRepository.delete(benefit);
    }
}
