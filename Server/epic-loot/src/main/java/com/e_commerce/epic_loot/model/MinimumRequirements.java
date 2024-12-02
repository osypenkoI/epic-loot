package com.e_commerce.epic_loot.model;

import com.e_commerce.epic_loot.enumEntity.RamMemory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "minimum_requirements")
public class MinimumRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "operating_system", nullable = false, length = 50)
    private String operatingSystem;

    @Column(name = "processor", nullable = false, length = 50)
    private String processor;

    @Enumerated(EnumType.STRING)
    @Column(name = "ram_memory", nullable = false, length = 20)
    private RamMemory ramMemory;

    @Column(name = "graphic_card", nullable = false, length = 50)
    private String graphicCard;

    @Column(name = "direct_x", nullable = false, length = 20)
    private String directX;

    @Column(name = "disk_space", nullable = false, length = 20)
    private String diskSpace;

    @Column(name = "extra")
    private String extra;

    @JsonIgnore
    @OneToOne(mappedBy = "minimumRequirements", orphanRemoval = true)
    private Product product;

}