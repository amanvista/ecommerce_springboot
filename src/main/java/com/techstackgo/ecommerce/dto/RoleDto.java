package com.techstackgo.ecommerce.dto;

import com.techstackgo.ecommerce.model.BaseEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RoleDto extends BaseEntity {
    private Long id;
    private String name;
}
