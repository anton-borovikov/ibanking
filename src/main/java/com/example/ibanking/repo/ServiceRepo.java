package com.example.ibanking.repo;

import com.example.ibanking.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Long>{
}
