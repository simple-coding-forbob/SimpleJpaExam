package com.simplecoding.jpaexam.dept.repository;


import com.simplecoding.jpaexam.dept.dto.DeptStatsDto;
import com.simplecoding.jpaexam.dept.entity.Dept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Dept,Integer> {
    //    TODO: 1) like 검색 , 페이징
    @Query(value = "select d from Dept d\n" +
            "where d.dname like %:searchKeyword%")
    Page<Dept> selectAll(
            @Param("searchKeyword") String searchKeyword,
            Pageable pageable
    );

//    TODO: 2) 부서 테이블에서 부서명(dname), 위치(loc)를 매개변수로 받아 조회
    @Query(value = "select d from Dept d\n" +
            "where d.dname = :dname\n" +
            "and   d.loc   = :loc")
    Page<Dept> selectByDnameAndLoc(
            @Param("dname") String dname,
            @Param("loc") String loc,
            Pageable pageable
    );

//    TODO: 3) 부서테이블의 부서번호를 sum, avg, max, min 값을 화면에 표시
//       단, sum(Long), avg(Double), max(Integer), min(Integer)
    @Query(value = "select new com.simplecoding.jpaexam.dept.dto.DeptStatsDto(sum(d.dno),avg(d.dno),max(d.dno),min(d.dno))\n" +
            "from Dept d")
    DeptStatsDto selectGroup();
}
