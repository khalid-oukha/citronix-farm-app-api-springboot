package com.citronix.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime date;
    private double unitPrice;
    private double quantity;
    private String client;
    private double Revenue;

    @ManyToOne
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }
}
