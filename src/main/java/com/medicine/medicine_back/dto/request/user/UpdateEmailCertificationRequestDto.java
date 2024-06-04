package com.medicine.medicine_back.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEmailCertificationRequestDto {
    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;
}
