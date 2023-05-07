package com.gaerine.triple.web.controller.board;

import com.gaerine.triple.ApiKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MapController {

    private final ApiKey apiKey;

    @PostMapping("/requsetPlace")
    public ResponseEntity<Map<String,Object>> requestAPI(@RequestBody String req){

        String url ="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?";
        String key =apiKey.getApiKey();

        WebClient client = WebClient.builder().
                baseUrl(url)
                .build();

        Map<String,Object> result = client.get().uri(data -> data
                .queryParam("input", req)
                .queryParam("inputtype", "textquery")
                .queryParam("fields", "formatted_address,name,rating,opening_hours,geometry")
                .queryParam("key", key).build()
        ).retrieve().bodyToMono(Map.class).block();

        log.info("result={}",result);

        return ResponseEntity.ok().body(result);
    }
}
