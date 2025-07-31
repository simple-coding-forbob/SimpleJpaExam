package com.simplecoding.jpaexam.faq.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simplecoding.jpaexam.dept.dto.DeptStatsDto;
import com.simplecoding.jpaexam.dept.entity.Dept;
import com.simplecoding.jpaexam.dept.entity.QDept;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FaqRepositoryDsl {

    //    (참고) queryDsl 사용: 원래는 파일 분리하지만, 간단히 사용법만 소개하기 위해 서비스에서 사용합니다.
    private final JPAQueryFactory queryFactory;

    QDept dept = QDept.dept;

//    TODO: 1)
    public Page<Dept> queryByDnameAndLoc(String dname, String loc, Pageable pageable) {

        // 기본 쿼리 + 페이징 처리 + 조건 + 데이터 조회
        List<Dept> content = queryFactory
                .selectFrom(dept)
                .where(dept.dname.eq(dname).and(dept.loc.eq(loc))) // 고정된 조건
                .offset(pageable.getOffset()) // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .orderBy(dept.dno.asc()) // 정렬
                .fetch();

        // 전체 개수 조회 (전체 데이터 개수)
        long total = queryFactory
                .select(dept.count())
                .from(dept)
                .where(dept.dname.eq(dname).and(dept.loc.eq(loc)))
                .fetchOne();

        // PageImpl로 페이징된 결과 반환
        return new PageImpl<>(content, pageable, total);
    }

//    TODO: 2)
    public DeptStatsDto queryGroup() {

        // QueryDSL에서 직접 DeptStatsDto 생성
        return queryFactory
                .select(
                        Projections.constructor(
                                DeptStatsDto.class,  // DeptStatsDto의 생성자
                                dept.dno.sum(),      // sum(d.dno)
                                dept.dno.avg(),      // avg(d.dno)
                                dept.dno.max(),      // max(d.dno)
                                dept.dno.min()       // min(d.dno)
                        )
                )
                .from(dept)
                .fetchOne();  // 결과는 단 하나의 DTO (DeptStatsDto)만 반환
    }

    //    TODO: 3) dname 또는 loc 파라미터가 있으면 해당 조건만 검색
    //           둘 다 없으면 전체 조회(다이나믹 쿼리)
    public PageImpl<Dept> queryByDnameOrLoc(String dname, String loc, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

//      dname.isBlank() : "", "  " 등 의미
        if (dname != null && !dname.isBlank()) {
            builder.and(dept.dname.eq(dname));
        }

        if (loc != null && !loc.isBlank()) {
            builder.and(dept.loc.eq(loc));
        }

        // 기본 쿼리 + 페이징 처리 + 조건 + 데이터 조회
        List<Dept> content = queryFactory
                .selectFrom(dept)
                .where(builder)
                .fetch();

        // 전체 개수 조회 (전체 데이터 개수)
        long total = queryFactory
                .select(dept.count())
                .from(dept)
                .where(builder)
                .fetchOne();

        // PageImpl로 페이징된 결과 반환
        return new PageImpl<>(content, pageable, total);
    }
}
