package com.citronix.api.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Farm {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String location;

    private double area;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Field> fields;
}
