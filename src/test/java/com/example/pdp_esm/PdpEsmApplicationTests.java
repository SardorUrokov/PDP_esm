package com.example.pdp_esm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class PdpEsmApplicationTests {

    private static final int id = 1;

    @Test
    void createCourse() {

        String url = "http://localhost:8989/api/course/create";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.set("Authorization", "Bearer, xxxxxxx");   //security bo'lganida qo'shiladi

        String json = """
                {
                  "courseName": "Java-Android Development",
                  "price": 450000,
                  "active": true,
                  "courseType": "HYBRID"
                }
                """;

        HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Object exchange = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        System.out.println(exchange);
    }

    @Test
    void readCourses() {

        String url = "http://localhost:8989/api/course";

        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization", "Bearer, xxxxxxx");
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Object exchange = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        System.out.println(exchange);

    }

    @Test
    void updateCourse() {

        String url = "http://localhost:8989/api/course/" + id;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.set("Authorization", "Bearer, xxxxxxx");   //security bo'lganida qo'shiladi

        String json = """
                {
                  "courseName": "FullStack Development",
                  "price": 1400000,
                  "active": true,
                  "courseType": "OFFLINE"
                }
                """;

        HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Object exchange = restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
        System.out.println(exchange);
    }

    @Test
    void deleteCourse() {

        String url = "http://localhost:8989/api/course/" + id;

        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization", "Bearer, xxxxxxx");
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Object exchange = restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
        System.out.println(exchange);
    }
}