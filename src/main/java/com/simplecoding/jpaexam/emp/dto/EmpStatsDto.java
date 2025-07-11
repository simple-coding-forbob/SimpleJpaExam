package com.simplecoding.jpaexam.emp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmpStatsDto {
    private Long sum;
    private Double avg;
    private Integer max;
    private Integer min;
}
