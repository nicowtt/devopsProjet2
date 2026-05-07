package com.openclassrooms.etudiant.mapper;

import com.openclassrooms.etudiant.dto.RegisterDTO;
import com.openclassrooms.etudiant.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-07T09:55:39+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public User toEntity(RegisterDTO registerDTO) {
        if ( registerDTO == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( registerDTO.getFirstName() );
        user.setLastName( registerDTO.getLastName() );
        user.setLogin( registerDTO.getLogin() );
        user.setPassword( registerDTO.getPassword() );

        return user;
    }
}
