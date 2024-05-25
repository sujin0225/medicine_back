package com.medicine.medicine_back.entity;

import com.medicine.medicine_back.dto.request.review.PutFavoriteRequestDto;
import com.medicine.medicine_back.entity.primaryKey.FavoritePK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorite")
@Table(name = "favorite")
@IdClass(FavoritePK.class)
public class FavoriteEntity {
    @Id
    private String itemSeq;
    @Id
    private String userId;
    private String ITEM_NAME;
    private String FORM_CODE_NAME;
    private String CLASS_NO;
    private String CLASS_NAME;
    private String ENTP_NAME;
    private String ETC_OTC_NAME;
    private String write_datetime;
    private String ITEM_IMAGE;
    private String ITEM_ENG_NAME;


    public FavoriteEntity(PutFavoriteRequestDto dto, String itemSeq, String userId) {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String write_datetime = simpleDateFormat.format(now);
        this.itemSeq = itemSeq;
        this.userId = userId;
        this.write_datetime = write_datetime;
        this.ITEM_NAME = dto.getITEM_NAME();
        this.FORM_CODE_NAME = dto.getFORM_CODE_NAME();
        this.CLASS_NO = dto.getCLASS_NO();
        this.CLASS_NAME = dto.getCLASS_NAME();
        this.ENTP_NAME = dto.getENTP_NAME();
        this.ETC_OTC_NAME = dto.getETC_OTC_NAME();
        this.ITEM_IMAGE = dto.getITEM_IMAGE();
        this.ITEM_ENG_NAME = dto.getITEM_ENG_NAME();
    }
}