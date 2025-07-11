package com.simplecoding.jpaexam.dept.entity;


import com.simplecoding.jpaexam.common.BaseTimeEntity;
import com.simplecoding.jpaexam.emp.entity.Emp2;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "TB_DEPT2")
@SequenceGenerator(
        name = "SQ_DEPT2_JPA",
        sequenceName = "SQ_DEPT2",
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "emp2")
@EqualsAndHashCode(of = "dno", callSuper = false)
public class Dept2 extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_DEPT2_JPA"
    )
    private Integer dno;   // 부서번호(기본키)
    private String  dname; // 부서명
    private String  loc;   // 부서위치

    @OneToMany(mappedBy = "dept2")
    Set<Emp2> emp2=new HashSet<>();
}

