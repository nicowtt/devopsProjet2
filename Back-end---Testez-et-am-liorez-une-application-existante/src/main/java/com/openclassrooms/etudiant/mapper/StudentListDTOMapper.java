package com.openclassrooms.etudiant.mapper;

import com.openclassrooms.etudiant.dto.StudentListDTO;
import com.openclassrooms.etudiant.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface StudentListDTOMapper {
    StudentListDTO toDTO(Student student);
}
