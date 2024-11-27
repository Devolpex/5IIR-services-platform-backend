package org._iir.backend.modules.user;

import org._iir.backend.interfaces.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements IMapper<User, UserDTO> {

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .role(dto.getRole())
                .accountVerified(dto.isAccountVerified())
                .build();
    }

    @Override
    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }
        return UserDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .email(entity.getEmail())
                .role(entity.getRole())
                .accountVerified(entity.isAccountVerified())
                .build();
    }

}
