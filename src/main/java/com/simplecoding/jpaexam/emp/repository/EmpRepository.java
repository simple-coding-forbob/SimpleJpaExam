package com.simplecoding.jpaexam.emp.repository;

import com.simplecoding.jpaexam.emp.dto.EmpStatsDto;
import com.simplecoding.jpaexam.emp.entity.Emp;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepository extends JpaRepository<Emp,Integer> {

    //    TODO: 2) commission(상여금 컬럼) 이 null 이고 salary >= 1000(임의의 값) 인 값을 조회하세요
//       단, sum(Long), avg(Double), max(Integer), min(Integer)
    @Query(value = "select e from Emp e\n" +
            "where e.commission is null\n" +
            "and   e.salary >= :salary")
    Page<Emp> selectSalary(@Param("salary") int salary,Pageable pageable);

    //    TODO: 3) salary(급여)를 sum, avg, max, min 값을 화면에 표시하세요
    @Query(value = "select new com.simplecoding.jpaexam.emp.dto.EmpStatsDto(sum(e.salary),avg(e.salary),max(e.salary),min(e.salary))\n" +
            "from Emp e")
    EmpStatsDto selectGroup();

    //    TODO: 예제) like 검색: (lazy(지연 기능)기능의 단점) N+1 문제 => join fetch 사용
    @Query(value = "select e from Emp e join fetch e.dept d\n" +
            "where e.ename like %:searchKeyword%")
    Page<Emp> selectAll(
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable
    );

    //    TODO: 4) bulk sql: delete 직접 하기(db에 바로 반영)
//       delete 문 1개만 실행됩니다.
    @Transactional
    @Modifying
    @Query(value = "delete Emp where eno=:eno")
    void bulkDelete(@Param("eno") int eno);
}
