package com.kohub.coworking.repository;

import com.kohub.coworking.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByCodeIgnoreCase(String code);
} 