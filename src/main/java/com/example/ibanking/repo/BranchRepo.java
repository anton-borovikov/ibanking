package com.example.ibanking.repo;

import com.example.ibanking.domain.Branch;
import com.example.ibanking.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepo extends JpaRepository<Branch, Long> {
    List<Branch> findByCity(City city);
}
