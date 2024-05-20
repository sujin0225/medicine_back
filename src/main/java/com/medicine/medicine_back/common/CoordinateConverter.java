package com.medicine.medicine_back.common;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;

import java.util.Collections;
import java.util.Optional;

public class CoordinateConverter {
    private CoordinateTransform transform;

    public CoordinateConverter() {
        // CRSFactory 인스턴스 생성
        CRSFactory crsFactory = new CRSFactory();

        // TM과 WGS84 좌표계 정의
        CoordinateReferenceSystem crsTM = crsFactory.createFromName("EPSG:5186");
        CoordinateReferenceSystem crsWGS84 = crsFactory.createFromName("EPSG:4326");

        // 좌표 변환 객체 생성
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        transform = ctFactory.createTransform(crsTM, crsWGS84);
    }

    public double[] convertTMToWGS84(String xStr, String yStr) {
        xStr = xStr == null ? "" : xStr;
        System.out.println("getX:" + xStr);

        yStr = yStr == null ? "" : yStr;
        System.out.println("getY:" + yStr);

        // String 값을 double로 변환
        double x = Double.parseDouble(xStr);
        double y = Double.parseDouble(yStr);

        // 변환할 TM 좌표 생성
        ProjCoordinate tmCoord = new ProjCoordinate(x, y);

        // 변환된 WGS84 좌표를 저장할 객체 생성
        ProjCoordinate wgs84Coord = new ProjCoordinate();

        // 좌표 변환 수행
        transform.transform(tmCoord, wgs84Coord);

        // 변환된 위도, 경도 반환
        return new double[]{wgs84Coord.y, wgs84Coord.x};
    }
}