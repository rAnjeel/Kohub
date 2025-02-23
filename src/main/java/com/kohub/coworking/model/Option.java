package com.kohub.coworking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "options")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class Option {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_option;
    
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    
    @Column(name = "option_nom", nullable = false)
    private String name;
    
    @Column(name = "prix", nullable = false)
    private Double price;
} 