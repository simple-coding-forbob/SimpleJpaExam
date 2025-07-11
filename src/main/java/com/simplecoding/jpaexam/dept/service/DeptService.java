package com.simplecoding.jpaexam.dept.service;

import com.simplecoding.jpaexam.common.MapStruct;
import com.simplecoding.jpaexam.dept.dto.DeptDto;
import com.simplecoding.jpaexam.dept.dto.DeptStatsDto;
import com.simplecoding.jpaexam.dept.entity.Dept;
import com.simplecoding.jpaexam.dept.repository.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeptService {

    //    DB CRUD 클래스 받기 : JPA 제공 함수 사용 가능
    private final DeptRepository deptRepository;
    private final MapStruct mapStruct;

//    TODO: 1) JPA 기본 메소드들: SQL 작성없이 자동으로 만들어서 실행됩니다.
//    TODO: (1) 상세조회: findById(기본키)
    public DeptDto findById(int dno) {
//        JPA 상세조회 함수 실행
        Dept dept=deptRepository.findById(dno)
                                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다."));

        return mapStruct.toDto(dept);
    }

//    TODO: (2) 전체조회: findAll()
    public List<DeptDto> findAll() {
        List<Dept> list= deptRepository.findAll();
        return list.stream().map(dept->mapStruct.toDto(dept)).toList();
    }

    //    TODO: (3) 전체조회: findAll(Pageable pageable)
    public Page<DeptDto> findAll(Pageable pageable) {
        Page<Dept> page= deptRepository.findAll(pageable);
        return page.map(dept->mapStruct.toDto(dept));
    }

//        TODO: (4) 저장/수정: save(클래스 변수)
//                     엔티티클래스에 기본키값이 없으면 추가, 있으면 수정이 자동으로 SQL 작성되어 실행됩니다.
//                 화면에서 전달된 null 값도 수정됩니다.(주의)
    public void save(DeptDto deptDto) {
//        JPA 저장 함수 실행 : return 값 : 저장된 객체
        Dept dept=mapStruct.toEntity(deptDto);
        deptRepository.save(dept);
    }

//        TODO: (4) 수정: (dirty checking 기능 사용)
//             dirty checking 기능: JPA 에서 save() 실행 없이 setter 로 엔티티 값을 수정하면 update 문이 실행됩니다.
//             수정할 값이 동일하면 update 문이 실행되지 않습니다.(성능 증가)
//             mapStruct 라이브러리는 setter 를 자동으로 만들어주는 라이브러리이므로 dirty checking 기능이 작동합니다.
//             사용법: 1) @Transactional 사용: 여러개의 SQL 문 사용시 그룹으로 묶어서 트랜잭션 시작 ~ 종료됩니다.
//                    2) 먼저 엔티티 조회: JPA 에 현재 결과가 임시 저장됩니다.(캐싱)
//                    3) setter 사용: 직접코딩 또는 라이브러리 이용(임시 저장값 vs 화면값 비교해서 다르면 update 문이 실행됩니다.)
    @Transactional
    public void updateFromDto(DeptDto deptDto) {
//        JPA 저장 함수 실행 : return 값 : 저장된 객체
        Dept dept=deptRepository.findById(deptDto.getDno())
                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다."));

        mapStruct.updateFromDto(deptDto, dept);
//        deptRepository.save(dept);     // dirty checking 으로 인해 필요없음
    }
    //     TODO: (6) 삭제: deleteById(기본키)
//                     삭제가 자동으로 SQL 작성되어 실행됩니다.
    public void deleteById(int dno) {
        deptRepository.deleteById(dno);
    }

//    TODO: 2) JPA 에서 SQL 직접 작성하기: SQL 과 비슷한 JPQL 로 작성합니다.
    //  TODO: (1)
    public Page<DeptDto> selectByDnameAndLoc(String dname, String loc, Pageable pageable) {
        Page<Dept> page= deptRepository.selectByDnameAndLoc(dname, loc, pageable);
        return page.map(dept -> mapStruct.toDto(dept));
    }
    //  TODO: (2)
    public DeptStatsDto selectGroup() {
        return deptRepository.selectGroup();
    }

    //  TODO: (3)
    public Page<DeptDto> selectAll(String searchKeyword, Pageable pageable) {
        Page<Dept> page= deptRepository.selectAll(searchKeyword, pageable);
        return page.map(dept -> mapStruct.toDto(dept));
    }

//    TODO: (4)
    public void bulkDelete(int dno) {
        deptRepository.bulkDelete(dno);
    }
}

