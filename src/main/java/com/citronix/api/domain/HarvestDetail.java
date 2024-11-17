package com.citronix.api.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HarvestDetail {

    @Id
    @GeneratedValue
    private Long id;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "tree_id",nullable = false)
    private Tree tree;

    @ManyToOne
    @JoinColumn(name = "harvest_id",nullable = false)
    private Harvest harvest;
}
