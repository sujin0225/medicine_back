package com.medicine.medicine_back.common;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);

    @Value("${kakao.map.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double[] getCoordinates(String address) {
        logger.info("Geocoding address: {}", address); // 입력된 주소 로그

        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Response received: {}", response.getBody()); // 응답 로그

            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray documents = jsonObject.getJSONArray("documents");
            if (documents.length() > 0) {
                JSONObject addressInfo = documents.getJSONObject(0);
                double x = addressInfo.getDouble("x");
                double y = addressInfo.getDouble("y");
                logger.info("Coordinates found: x={}, y={}", x, y); // 좌표 로그
                return new double[]{x, y};
            } else {
                logger.warn("No documents found for address: {}", address);
            }
        } else {
            logger.error("Failed to get response from API. Status code: {}", response.getStatusCode());
        }
        return null;
    }
}
