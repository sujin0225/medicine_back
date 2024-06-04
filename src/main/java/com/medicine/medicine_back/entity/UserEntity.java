package com.medicine.medicine_back.entity;

import com.medicine.medicine_back.dto.request.auth.SignUpRequestDto;
import com.medicine.medicine_back.dto.request.user.PatchEmailRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
@Table(name = "user")
public class UserEntity {
    @Id
    private String userId;
    private String password;
    private String email;
    private String type;

    public UserEntity(SignUpRequestDto dto) {
        this.userId = dto.getId();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.type = "app";
    }

    public UserEntity(String userId, String email, String type) {
        this.userId = userId;
        this.password = "Passw0rd!";
        this.email = email;
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}