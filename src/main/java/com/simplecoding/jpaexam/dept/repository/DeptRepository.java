package com.simplecoding.jpaexam.dept.repository;


import com.simplecoding.jpaexam.dept.dto.DeptStatsDto;
import com.simplecoding.jpaexam.dept.entity.Dept;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Dept,Long> {

//    TODO: 1) 부서 테이블에서 부서명(dname), 위치(loc)를 매개변수로 받아 조회
    @Query(value = "select d from Dept d\n" +
            "where d.dname = :dname\n" +
            "and   d.loc   = :loc")
    Page<Dept> selectByDnameAndLoc(
            @Param("dname") String dname,
            @Param("loc") String loc,
            Pageable pageable
    );

//    TODO: 2) 부서테이블의 부서번호를 sum, avg, max, min 값을 화면에 표시
//       단, sum(Long), avg(Double), max(Long), min(Long)
    @Query(value = "select new com.simplecoding.jpaexam.dept.dto.DeptStatsDto(sum(d.dno),avg(d.dno),max(d.dno),min(d.dno))\n" +
            "from Dept d")
    DeptStatsDto selectGroup();

    //    TODO: 3) like 검색 , 페이징
    @Query(value = "select d from Dept d\n" +
            "where d.dname like %:searchKeyword%")
    Page<Dept> selectAll(
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable
    );

//    TODO: 4) bulk sql: delete 직접 하기(db에 바로 반영)
//       delete 문 1개만 실행됩니다.
    @Transactional
    @Modifying
    @Query(value = "delete Dept where dno=:dno")
    void bulkDelete(@Param("dno") long dno);
}
