package com.ecommerce.homeproducts.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class ImgbbService {

    private static final String API_KEY = "48c08a53b2ef7aed7f2bf14a0e0f5225";
    private static final String UPLOAD_URL = "https://api.imgbb.com/1/upload";

    public String uploadToImgbb(MultipartFile file) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("key", API_KEY);
            body.add("image", file.getResource());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Map> response = restTemplate.exchange(
                    UriComponentsBuilder.fromHttpUrl(UPLOAD_URL).build().toUri(),
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map data = (Map) response.getBody().get("data");
            return data.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Error uploading to ImgBB", e);
        }
    }
}
