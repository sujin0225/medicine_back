package com.medicine.medicine_back.dto.request.review;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PutFavoriteRequestDto {
    @NotBlank
    private String ITEM_NAME;
    @NotBlank
    private String FORM_CODE_NAME;
    @NotBlank
    private String CLASS_NO;
    @NotBlank
    private String CLASS_NAME;
    @NotBlank
    private String ENTP_NAME;
    @NotBlank
    private String ETC_OTC_NAME;
    @NotBlank
    private String write_datetime;
    @NotBlank
    private String ITEM_IMAGE;
    @NotBlank
    private String ITEM_ENG_NAME;
}
