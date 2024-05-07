package com.medicine.medicine_back.dto.request.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostReviewRequestDto {

    @NotNull
    private Integer starRating;
    @NotBlank
    private String content;
    @NotNull
    private List<String> reviewImageList;
//    @NotNull
//    private Integer helpfulCount;
}
