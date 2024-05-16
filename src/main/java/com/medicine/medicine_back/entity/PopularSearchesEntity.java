package com.medicine.medicine_back.entity;

import com.medicine.medicine_back.dto.request.search.PostSearchRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "popular_searches")
@Table(name = "popular_searches")
public class PopularSearchesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int searchNumber;
    private String searchWord;
//    private String searchCount;

//    public PopularSearchesEntity(PostSearchRequestDto dto, String searchWord) {
//        this.searchWord = searchWord;
//    }
    public PopularSearchesEntity(PostSearchRequestDto dto, String searchWord) {
        this.searchWord = searchWord;
    }
}
