package com.simplecoding.jpaexam.emp.service;

import com.simplecoding.jpaexam.common.MapStruct;
import com.simplecoding.jpaexam.emp.dto.EmpDto;
import com.simplecoding.jpaexam.emp.dto.EmpStatsDto;
import com.simplecoding.jpaexam.emp.entity.Emp;
import com.simplecoding.jpaexam.emp.repository.EmpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpService {

    //    DB CRUD 클래스 받기 : JPA 제공 함수 사용 가능
    private final EmpRepository empRepository;
    private final MapStruct mapStruct;

//    TODO: 1) JPA 기본 메소드들: SQL 작성없이 자동으로 만들어서 실행됩니다.
//    TODO: (1) 상세조회: findById(기본키)
    public EmpDto findById(long eno) {
//        JPA 상세조회 함수 실행
        Emp emp= empRepository.findById(eno)
                .orElseThrow(() -> new RuntimeException("정보를 저장할 수 없습니다."));
        return mapStruct.toDto(emp);
    }

    //    TODO: (2) 전체조회: findAll()
    public List<EmpDto> findAll() {
        List<Emp> list= empRepository.findAll();
        return list.stream().map(emp->mapStruct.toDto(emp)).toList();
    }

    //    TODO: (3) 전체조회: findAll(Pageable pageable)
    public Page<EmpDto> findAll(Pageable pageable) {
        Page<Emp> page= empRepository.findAll(pageable);
        return page.map(emp->mapStruct.toDto(emp));
    }

//        TODO: (4) 저장/수정: save(클래스 변수)
//                     엔티티클래스에 기본키값이 없으면 추가, 있으면 수정이 자동으로 SQL 작성되어 실행됩니다.
    public void save(EmpDto empDto) {
//        JPA 저장 함수 실행 : return 값 : 저장된 객체
        Emp emp=mapStruct.toEntity(empDto);
        empRepository.save(emp);
    }

    @Transactional
    public void updateFromDto(EmpDto empDto) {
//        JPA 저장 함수 실행 : return 값 : 저장된 객체
        Emp emp=empRepository.findById(empDto.getEno())
                .orElseThrow(() -> new RuntimeException("정보를 찾을 수 없습니다."));

        mapStruct.updateFromDto(empDto, emp);
//        deptRepository.save(emp);     // dirty checking 으로 인해 필요없음
    }


//     TODO: (6) 삭제: deleteById(기본키)
//                     삭제가 자동으로 SQL 작성되어 실행됩니다.
    public void deleteById(long eno) {
        empRepository.deleteById(eno);
    }

//    TODO: 2) JPA 에서 SQL 직접 작성하기: SQL 과 비슷한 JPQL 로 작성합니다.
//    TODO: (1)
    public Page<EmpDto> selectSalary(long salary, Pageable pageable) {
        Page<Emp>  page= empRepository.selectSalary(salary, pageable);
        return page.map(emp -> mapStruct.toDto(emp));
    }
    //  TODO: (2)
    public EmpStatsDto selectGroup() {
        return empRepository.selectGroup();
    }
    //  TODO: (3)
    public Page<EmpDto> selectAll(String searchKeyword, Pageable pageable) {
        Page<Emp>  page= empRepository.selectAll(searchKeyword, pageable);
        return page.map(emp -> mapStruct.toDto(emp));
    }

    //    TODO: (4)
    public void bulkDelete(long eno) {
        empRepository.bulkDelete(eno);
    }

}

