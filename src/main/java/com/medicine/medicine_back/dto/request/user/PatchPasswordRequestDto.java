package com.medicine.medicine_back.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchPasswordRequestDto {
    @NotBlank
    @Pattern(regexp ="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?])[a-zA-Z0-9!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?]{8,12}$")
    private String password;
}
