package com.medicine.medicine_back.service;

import com.medicine.medicine_back.dto.request.search.PostSearchRequestDto;
import com.medicine.medicine_back.dto.response.search.GetPopularListResponseDto;
import com.medicine.medicine_back.dto.response.search.PostSearchResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface SearchService {
    ResponseEntity<? super GetPopularListResponseDto> getPopularList();
    ResponseEntity<? super PostSearchResponseDto> postSearch(PostSearchRequestDto dto,@PathVariable String searchWord);
}
