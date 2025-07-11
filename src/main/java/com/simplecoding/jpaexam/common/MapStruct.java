package com.simplecoding.jpaexam.common;

import com.simplecoding.jpaexam.dept.dto.DeptDto;
import com.simplecoding.jpaexam.dept.entity.Dept;
import com.simplecoding.jpaexam.emp.dto.EmpDto;
import com.simplecoding.jpaexam.emp.entity.Emp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE  // null 제외 기능(update 시 사용)
)
public interface MapStruct {
    // TODO: 1) Emp <-> EmpDto
    @Mapping(source = "dept.dno", target = "dno")
    EmpDto toDto(Emp emp);
    @Mapping(source = "dno", target = "dept.dno")
    Emp toEntity(EmpDto empDto);
    // TODO: 수정 시 사용: dirty checking 기능(save() 없이 수정 가능)
    void updateFromDto(DeptDto deptDto, @MappingTarget Dept dept);
    // TODO: 수정 시 사용: dirty checking 기능(save() 없이 수정 가능)
//      경고 무시하고 싶으면 아래 추가하세요
    @Mapping(target = "dept", ignore = true)
    void updateFromDto(EmpDto empDto, @MappingTarget Emp emp);

    // TODO: 2) Dept <-> DeptDto
    DeptDto toDto(Dept dept);
    Dept toEntity(DeptDto deptDto);
}
