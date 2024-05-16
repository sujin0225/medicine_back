package com.medicine.medicine_back.controller;

import com.medicine.medicine_back.dto.request.search.PostSearchRequestDto;
import com.medicine.medicine_back.dto.response.search.GetPopularListResponseDto;
import com.medicine.medicine_back.dto.response.search.PostSearchResponseDto;
import com.medicine.medicine_back.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    //인기 검색어
    @GetMapping("/popular-list")
    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
        ResponseEntity<? super GetPopularListResponseDto> response = searchService.getPopularList();
        return response;
    }

    //검색어 저장
    @PostMapping("/{searchWord}")
    public ResponseEntity<? super PostSearchResponseDto>  postSearch(
            @RequestBody PostSearchRequestDto requestBody,
            @PathVariable("searchWord") String searchWord
            ) {
        ResponseEntity<? super PostSearchResponseDto> response = searchService.postSearch(requestBody, searchWord);
        return response;
    }
}
