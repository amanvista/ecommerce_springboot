package com.techstackgo.ecommerce.model;

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
