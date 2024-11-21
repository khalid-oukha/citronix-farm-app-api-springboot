package com.citronix.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Tree {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime plantationDate;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @OneToMany(mappedBy = "tree")
    private List<HarvestDetail> harvestDetails;

}
