package com.simplecoding.jpaexam.emp.entity;

import com.simplecoding.jpaexam.common.BaseTimeEntity;
import com.simplecoding.jpaexam.dept.entity.Dept2;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "TB_EMP2")
@SequenceGenerator(
        name = "SQ_EMP2_JPA",
        sequenceName = "SQ_EMP2",
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "eno", callSuper = false)
public class Emp2 extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_EMP2_JPA"
    )
    private Long eno;                // 사원번호(기본키, 시퀀스)
    private String ename;               // 사원명
    private String job;                 // 직위
    private Long manager;            // 관리자사원번호
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredate;         // 입사일 (화면에서 위와 같이 전달됨, 기본값 형태이므로 생략가능)
    private Long salary;             // 급여
    private Long commission;         // 보너스(상여금)
    // 단방향 조인 (Emp -> Dept)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dno")           // DB FK 컬럼명 작성
    private Dept2 dept2;                // 부모 엔티티 클래스명

//     TODO: 양방향 연관관계 필드에 대해 추가/수정/삭제가 있을때는 양쪽 모두 처리해야 합니다.
//     TODO: 그래서 양쪽 모두 처리되어 있는 편의메소드를 작성해서 사용합니다.
//     TODO: 1) 수정
    public void changeDept(Dept2 newDept) {
        if (this.dept2 != null) {
            this.dept2.removeEmp(this); // 기존 부서에서 사원 제거
        }
        this.dept2 = newDept;
        if (newDept != null) {
            newDept.addEmp(this); // 새로운 부서에 사원 추가
        }
    }
}
