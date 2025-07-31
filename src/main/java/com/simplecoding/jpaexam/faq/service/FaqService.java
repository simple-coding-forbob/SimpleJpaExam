package com.simplecoding.jpaexam.faq.service;

import com.simplecoding.jpaexam.common.MapStruct;
import com.simplecoding.jpaexam.dept.dto.DeptDto;
import com.simplecoding.jpaexam.dept.dto.DeptStatsDto;
import com.simplecoding.jpaexam.dept.entity.Dept;
import com.simplecoding.jpaexam.dept.repository.DeptRepository;
import com.simplecoding.jpaexam.dept.repository.DeptRepositoryDsl;
import com.simplecoding.jpaexam.faq.entity.Faq;
import com.simplecoding.jpaexam.faq.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqService {

    //    DB CRUD 클래스 받기 : JPA 제공 함수 사용 가능
    private final FaqRepository faqRepository;

//    TODO: 1) JPA 기본 메소드들: SQL 작성없이 자동으로 만들어서 실행됩니다.
//    TODO: (1) 상세조회: findById(기본키)
    public Faq findById(long dno) {
//        JPA 상세조회 함수 실행
        Faq faq=faqRepository.findById(dno).get();

        return faq;
    }
}

