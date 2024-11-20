package com.citronix.api.web.VM.tree;

import com.citronix.api.domain.enums.TreeStatus;
import com.citronix.api.web.VM.ResponseFieldVM;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTreeVM {
    private Long age;
    private TreeStatus status;
    private ResponseFieldVM field;
    private LocalDateTime plantationDate;

    public void calculateAge(LocalDateTime plantationDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        Period period = Period.between(plantationDate.toLocalDate(), currentDate.toLocalDate());
        this.age = (long) period.getYears();
    }
}
