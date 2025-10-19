package com.authService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// UserRole entity maps to the roles table in the relational database
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleId; // primary key

    private String name; // name of the role
}
