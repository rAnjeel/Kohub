package com.kohub.coworking.repository;

import com.kohub.coworking.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Optional<Paiement> findByRefPaiement(String refPaiement);
} 