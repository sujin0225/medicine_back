package com.medicine.medicine_back.dto.request.auth;

import com.medicine.medicine_back.entity.CertificationEntity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Pattern(regexp ="^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,12}$")
    private String id;

    @NotBlank
    @Pattern(regexp ="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?])[a-zA-Z0-9!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?]{8,12}$")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String CertificationNumber;
}
