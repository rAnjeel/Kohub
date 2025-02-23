package com.kohub.coworking.repository;

import com.kohub.coworking.model.Espace;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EspaceRepository extends JpaRepository<Espace, Long> {
    Optional<Espace> findByName(String name);
} 