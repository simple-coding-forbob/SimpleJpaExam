package com.simplecoding.jpaexam.dept.entity;


import com.simplecoding.jpaexam.common.BaseTimeEntity;
import com.simplecoding.jpaexam.emp.entity.Emp2;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private Long dno;   // 부서번호(기본키)
    private String  dname; // 부서명
    private String  loc;   // 부서위치

    @OneToMany(mappedBy = "dept2")
    List<Emp2> emp2=new ArrayList<>();

//     TODO: 양방향 연관관계 필드에 대해 추가/수정/삭제가 있을때는 양쪽 모두 처리해야 합니다.
//     TODO: 그래서 양쪽 모두 처리되어 있는 편의메소드를 작성해서 사용합니다.
//     TODO: 1) 추가
    public void addEmp(Emp2 emp) {
        if (!emp2.contains(emp)) {
            emp2.add(emp);
            emp.setDept2(this);
        }
    }

    //     TODO: 2) 제거
    public void removeEmp(Emp2 emp) {
        emp2.remove(emp);
        emp.setDept2(null);
    }
}

