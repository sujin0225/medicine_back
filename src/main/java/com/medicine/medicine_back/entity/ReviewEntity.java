package com.medicine.medicine_back.entity;

import com.medicine.medicine_back.dto.request.review.PostReviewRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="review")
@Table(name="review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewNumber;
    private String userId;
    private String ITEM_SEQ;
    private String content;
    private String writeDatetime;
    private int starRating;

    public ReviewEntity(PostReviewRequestDto dto, String ITEM_SEQ, String userId) {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.ITEM_SEQ = ITEM_SEQ;
        this.userId = userId;
        this.content = dto.getContent();
        this.starRating = dto.getStarRating();
        this.writeDatetime = writeDatetime;
    }
}
