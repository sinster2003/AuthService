package com.authService.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // implements builder design pattern - contains an inner class to abstract complex object creation
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // serialises to snake case instead of default camel case
@Table(name = "tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // delegates the id generation to the database
    private Long id; // Generated automatically by DB using auto increment

    private String token; // refresh token

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id") // user_id is the foreign key
    private UserInfo userInfo;
}