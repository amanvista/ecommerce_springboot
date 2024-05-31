package com.techstackgo.ecommerce.service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.techstackgo.ecommerce.model.Role;
import com.techstackgo.ecommerce.model.RoleDto;
import com.techstackgo.ecommerce.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public List<RoleDto> getRoles() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<Role> getById(Long id) {
        return repository.findById(id);
    }

    public RoleDto createRole(RoleDto dto) {
        Role role = toEntity(dto);
        Role savedRole = repository.save(role);
        return toDto(savedRole);
    }

    public RoleDto updateRole(RoleDto dto) {
        var existing = getById(dto.getId());
        if (existing.isEmpty()) {
            throw new EntityNotFoundException();
        }
        updatePartial(existing.get(), dto);
        Role updatedRole = repository.save(existing.get());
        return toDto(updatedRole);
    }

    private RoleDto toDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        // Add other fields if necessary
        return dto;
    }

    private Role toEntity(RoleDto dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        // Add other fields if necessary
        return role;
    }

    private void updatePartial(Role existing, RoleDto dto) {
        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        // Update other fields if necessary
    }
}
