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

    @PostMapping("/requestPlace")
    public ResponseEntity<Map<String,Object>> requestAPI(@RequestBody Map<String,String> req){

        /* 단일장소 조회 */
//        String url ="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?";

        /* 여러장소 조회*/
        String url ="https://maps.googleapis.com/maps/api/place/textsearch/json?";
        String key =apiKey.getApiKey();

        /* 다낭 좌표 단일장소 */
//        String location = "circle:2000@"+req.get("lat") + "," + req.get("lng");
//        queryParam("locationbias",location)
//        .queryParam("inputtype", "textquery")
//          query -> input 쿼리 name 변경

        WebClient client = WebClient.builder().
                baseUrl(url)
                .build();

        Map<String,Object> result = client.get().uri(data -> data
                .queryParam("query", req.get("place"))
                .queryParam("location",req.get("lat")+","+req.get("lng"))
                .queryParam("radius",2000)
                .queryParam("fields", "formatted_address,name,rating,opening_hours,geometry")
                .queryParam("key", key).build()
        ).retrieve().bodyToMono(Map.class).block();

        log.info("result={}",result);
        return ResponseEntity.ok().body(result);
    }
}
