package com.medicine.medicine_back.service.implement;

import com.medicine.medicine_back.dto.request.search.PostSearchRequestDto;
import com.medicine.medicine_back.dto.response.ResponseDto;
import com.medicine.medicine_back.dto.response.search.GetPopularListResponseDto;
import com.medicine.medicine_back.dto.response.search.PostSearchResponseDto;
import com.medicine.medicine_back.entity.PopularSearchesEntity;
import com.medicine.medicine_back.repository.PopularSearchRepository;
import com.medicine.medicine_back.repository.resultSet.GetPopularListResultSet;
import com.medicine.medicine_back.service.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImplement implements SearchService {

    private final PopularSearchRepository popularSearchRepository;

    //인기 검색어 불러오기
    @Override
    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
        List<GetPopularListResultSet> resultSets = new ArrayList<>();
        try {
            resultSets = popularSearchRepository.getPopularList();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetPopularListResponseDto.success(resultSets);
    }

    //검색어 저장
    @Override
    public ResponseEntity<? super PostSearchResponseDto> postSearch(PostSearchRequestDto dto, String searchWord) {
        try {
            PopularSearchesEntity popularSearchesEntity = new PopularSearchesEntity(dto, searchWord);
            popularSearchRepository.save(popularSearchesEntity);

            int searchNumber = popularSearchesEntity.getSearchNumber();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostSearchResponseDto.success();
    }
}
